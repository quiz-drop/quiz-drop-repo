package com.sparta.quizdemo.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.chat.repository.ChatMessageRepository;
import com.sparta.quizdemo.common.entity.User;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.sse.service.NotificationService;
import com.sparta.quizdemo.user.UserRepository;
import com.sparta.quizdemo.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * 메시지 구독 및 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객체로 매핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            chatMessageRepository.save(roomMessage);

            String url = "";
            String senderUsername = roomMessage.getSender();
            Optional<User> senderUser = userService.findByUsername(senderUsername);
            if (senderUser.isPresent()) {
                User receiver = senderUser.get();
                String content = receiver.getUsername() + "님! 채팅 알림이 도착했어요!";
                notificationService.send(receiver, NotificationType.CHAT, content, url);
            } else {
                log.error("User not found: " + senderUsername);
                throw new IllegalArgumentException("존재하지 않는 유저입니다.");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // 관리자 역할("ADMIN")을 가진 사용자에게 메시지를 전송하는 메서드 추가
    public void sendMessageToAdmins(ChatMessage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend("adminMessages", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Failed to send message to admins: " + e.getMessage());
        }
    }
}