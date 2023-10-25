package com.sparta.quizdemo.chat.dto;

import com.sparta.quizdemo.chat.entity.ChatRooms;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String roomId;

    public ChatRoomResponseDto(ChatRooms chatRooms) {
        this.id = chatRooms.getId();
        this.roomId = chatRooms.getRoomId();
    }
}