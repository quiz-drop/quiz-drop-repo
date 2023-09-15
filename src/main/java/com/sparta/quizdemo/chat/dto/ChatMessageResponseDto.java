package com.sparta.quizdemo.chat.dto;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private String roomId;
    private String username;
    private String message;
    private String formattedTimestamp;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.username = chatMessage.getUsername();
        this.message = chatMessage.getMessage();
        this.formattedTimestamp = chatMessage.getFormattedTimestamp();
    }
}