package com.sparta.quizdemo.chat.dto;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String username;
    private String message;
    private String timeStamped;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.username = chatMessage.getUsername();
        this.message = chatMessage.getMessage();
        this.timeStamped = LocalDateTime.now().toString();
    }
}