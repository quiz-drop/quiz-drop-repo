package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.sse.service.NotificationService;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private NotificationService notificationService;

    /*
    * publicher
    * 해당하는 방에 메세지를 보냄
    */
    @MessageMapping("/chat/send")
    @SendTo
    public void sendMsg(@Payload Map<String, Object> data, ChatMessage message) {
        simpMessagingTemplate.convertAndSend("/topic/" + data.get("roomId"), data);
        System.out.println("data = " + data);

        String senderUsername = message.getUsername();
        Optional<User> senderUser = userService.findByUsername(senderUsername);

        if (senderUser.isPresent()) {
            User receiver = senderUser.get();
            String content = receiver.getUsername() + "님! 채팅 알림이 도착했어요!";
            String url = "/chatting?username=" + receiver.getUsername() + "&roomId=" + message.getRoomId();
            notificationService.send(receiver, NotificationType.CHAT, content, url);
        } else {
            log.error("User not found");
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
    }
}
