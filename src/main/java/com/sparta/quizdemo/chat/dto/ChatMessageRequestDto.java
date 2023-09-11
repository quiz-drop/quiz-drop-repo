package com.sparta.quizdemo.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {
    private String roomId;
    private String username;
    private String message;
}

