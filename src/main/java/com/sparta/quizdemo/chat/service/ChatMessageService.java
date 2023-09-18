package com.sparta.quizdemo.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    /* 메시지 저장 */
    public void messageSave(ChatMessageRequestDto requestDto, String roomId) throws JsonProcessingException {
        // 메시지 생성 및 시간 설정
        ChatMessage chatMessage = new ChatMessage(requestDto);
        chatMessage.setUsername(requestDto.getUsername());
        String messageJson = objectMapper.writeValueAsString(chatMessage);

        // Redis에서 해당 방의 채팅 메시지를 저장
        String redisKey = "messages:" + roomId;

        // 최신 메시지를 가장 마지막에 불러오기 위해 rightPush 사용
        redisTemplate.opsForList().rightPush(redisKey, messageJson);
    }

    /* 메시지 조회 */
    public List<ChatMessageResponseDto> getChatMessages(User user, String roomId) throws JsonProcessingException {
        List<ChatMessageResponseDto> chatMessages = new ArrayList<>();

        String redisKey = "messages:" + roomId;

        ScanOptions options = ScanOptions.scanOptions().match(redisKey).count(100).build();
        Cursor<String> cursor = redisTemplate.scan(options);

        while (cursor.hasNext()) {
            String messagesKey = cursor.next();
            List<String> messages = redisTemplate.opsForList().range(messagesKey, 0, -1);

            assert messages != null;
            for (String message : messages) {
                ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
                ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto(chatMessage);

                chatMessages.add(chatMessageResponseDto);
            }
        }
        cursor.close();
        return chatMessages;
    }

}
