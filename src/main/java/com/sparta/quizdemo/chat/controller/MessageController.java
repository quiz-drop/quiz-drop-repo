package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.common.entity.ChatMessage;
import com.sparta.quizdemo.chat.service.ChatRoomService;
import com.sparta.quizdemo.chat.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;

    /* websocket "/app/chat/message"로 들어오는 메시징을 처리한다. */
    @MessageMapping("/app/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomService.enterChatRoom(message.getRoomId());
            message.setMessage("환영합니다 " + message.getSender() + "님:D");
        }
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomService.getTopic(message.getRoomId()), message);
    }
}
