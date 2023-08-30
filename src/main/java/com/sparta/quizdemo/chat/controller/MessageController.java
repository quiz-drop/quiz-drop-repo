package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.chat.service.ChatRoomService;
import com.sparta.quizdemo.chat.service.RedisPublisher;
import com.sparta.quizdemo.chat.service.RedisSubscriber;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final RedisSubscriber redisSubscriber;
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;

    /* websocket "/app/chat/message"로 들어오는 메시징을 처리한다. */
    @MessageMapping("/app/chat/message")
    public void message(ChatMessage message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomService.enterChatRoom(message.getRoomId());
            message.setMessage("환영합니다 " + message.getSender() + "님:D");
        }

        // 발신자의 역할 정보 설정
        message.setRole(userDetails.getUser().getRole());

        // 관리자 역할을 가진 사용자에게만 메시지 전송
        if (message.getRole().equals(UserRoleEnum.USER)) {
            redisSubscriber.sendMessageToAdmins(message);
        }

        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomService.getTopic(message.getRoomId()), message);
    }
}
