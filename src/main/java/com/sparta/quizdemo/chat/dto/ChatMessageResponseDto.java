package com.sparta.quizdemo.chat.dto;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String roomId;
    private String username;
    private String message;
    private LocalDateTime createAt;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getChatRoom().getRoomId();
        this.username = chatMessage.getUser().getUsername();
        this.message = chatMessage.getMessage();
        this.createAt = chatMessage.getCreatedAt();
    }
}