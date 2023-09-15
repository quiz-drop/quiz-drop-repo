package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.chat.service.ChatRoomService;
import com.sparta.quizdemo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    /*
     * publicher
     * 해당하는 방에 메세지를 보냄
     */
    @MessageMapping("/chat/send")
    @SendTo
    public void sendMsg(@Payload Map<String, Object> data, ChatMessage message) {
        simpMessagingTemplate.convertAndSend("/topic/" + data.get("roomId"), data);
        System.out.println("data = " + data);
    }
}
