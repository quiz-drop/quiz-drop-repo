package com.sparta.quizdemo.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping("/chatRoom")
    public String getNextPage() {
        return "chatRoom";
    }

    @GetMapping("/chatting")
    public String getChatting() {
        return "chatting";
    }
}
