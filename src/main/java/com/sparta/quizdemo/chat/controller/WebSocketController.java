package com.sparta.quizdemo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    /// 해당하는 방에 메세지를 보낸것
    @MessageMapping("/chat/send")
    @SendTo
    public void sendMsg(@Payload Map<String, Object> data) {
        simpMessagingTemplate.convertAndSend("/topic/" + data.get("roomId"), data);
        System.out.println("data = " + data);
    }
}
