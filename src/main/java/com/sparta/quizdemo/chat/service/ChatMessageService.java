package com.sparta.quizdemo.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
        String redisKey = roomId + ":messages";

        // 최신 메시지를 가장 마지막에 불러오기 위해 rightPush 사용
        redisTemplate.opsForList().rightPush(redisKey, messageJson);
    }

    /* 메시지 조회 */
    public List<ChatMessageResponseDto> getChatMessages(User user, String roomId) throws JsonProcessingException {
        List<ChatMessageResponseDto> chatMessages = new ArrayList<>();

        String redisKey = roomId + ":messages";

        List<String> messageEntries = redisTemplate.opsForList().range(redisKey, 0, -1);

        assert messageEntries != null;

        for (String messageEntry : messageEntries) {
            ChatMessage chatMessage = objectMapper.readValue(messageEntry, ChatMessage.class);
            ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto(chatMessage);

            chatMessages.add(chatMessageResponseDto);
        }
        return chatMessages;
    }

}
