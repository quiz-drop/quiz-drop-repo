package com.sparta.quizdemo.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.common.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
/*
* 메시지 구독 및 처리
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Redis에서 메세지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메세지를 받아 처리
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객체로 매핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // WebSocket 구독자에게 채팅 메세지 Send
            messagingTemplate.convertAndSend("/app/chat/room/" + roomMessage.getRoomId(), roomMessage);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}