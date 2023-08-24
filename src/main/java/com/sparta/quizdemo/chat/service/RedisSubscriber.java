package com.sparta.quizdemo.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객체로 매핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);

            // 발신자의 역할(role) 확인
            if ("ADMIN".equals(roomMessage.getRole())) {
                // WebSocket 구독자에게 채팅 메시지 Send
                messagingTemplate.convertAndSend("/app/chat/room/" + roomMessage.getRoomId(), roomMessage);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // 관리자 역할("ADMIN")을 가진 사용자에게 메시지를 전송하는 메서드 추가
    public void sendMessageToAdmins(ChatMessage message) {
        // Redis pub/sub을 통해 메시지를 발행
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend("adminMessages", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Failed to send message to admins: " + e.getMessage());
        }
    }
}