package com.sparta.quizdemo.chat.dto;

import com.sparta.quizdemo.chat.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomResponseDto {
    private String roomId;
    private String username;

    public ChatRoomResponseDto(String roomId) {
        this.roomId = roomId;
    }
}
