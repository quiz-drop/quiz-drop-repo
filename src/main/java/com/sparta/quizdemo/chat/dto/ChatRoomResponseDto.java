package com.sparta.quizdemo.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomResponseDto {
    private String roomId;
    private String username;

    public ChatRoomResponseDto(String roomId, String username) {
        this.roomId = roomId;
        this.username = username;
    }
}
