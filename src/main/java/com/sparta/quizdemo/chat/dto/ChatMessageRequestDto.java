package com.sparta.quizdemo.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
    private String roomId;
    private String username;
    private String message;
}