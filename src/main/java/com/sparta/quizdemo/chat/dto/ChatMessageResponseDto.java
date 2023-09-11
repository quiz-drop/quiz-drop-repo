package com.sparta.quizdemo.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String roomId;
    private String username;
    private String message;
    private String messageId;
}