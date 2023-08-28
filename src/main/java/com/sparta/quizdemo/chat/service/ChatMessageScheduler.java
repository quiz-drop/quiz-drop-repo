package com.sparta.quizdemo.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.chat.repository.ChatMessageRepository;
import com.sparta.quizdemo.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageScheduler {
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 60000)
    public void migrateChatRoomsToMySQL() {
        List<ChatRoom> chatRooms = chatRoomService.findAllRooms();

        for (ChatRoom chatRoom : chatRooms) {
            try {
                chatRoomRepository.save(chatRoom);
                log.info("Saved ChatRoom to MySQL: " + chatRoom.getRoomId());

                String redisKey = "chatroom:" + chatRoom.getRoomId();
                redisTemplate.delete(redisKey);
            } catch (Exception e) {
                log.error("Failed to save ChatRoom to MySQL: " + e.getMessage());
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void migrateChatMessagesToMySQL() {
        List<ChatRoom> chatRooms = chatRoomService.findAllRooms();
        for (ChatRoom chatRoom : chatRooms) {
            String roomId = chatRoom.getRoomId();
            // Redis에서 해당 채팅 룸의 메시지를 가져오기 위한 Redis 키 생성
            String redisKey = "chatroom:" + roomId;

            // Redis 리스트에서 채팅 메시지 가져오기
            List<String> redisMessages = redisTemplate.opsForList().range(redisKey, 0, -1);

            // 가져온 Redis 메시지를 MySQL에 저장
            assert redisMessages != null;
            for (String redisMessage : redisMessages) {
                try {
                    ChatMessage chatMessage = objectMapper.readValue(redisMessage, ChatMessage.class);
                    chatMessageRepository.save(chatMessage);
                } catch (Exception e) {
                    log.error("Failed to save chat message to MySQL: " + e.getMessage());
                }
            }

            // MySQL에 저장한 메시지는 Redis에서 삭제
            redisTemplate.delete(redisKey);
        }
    }
}