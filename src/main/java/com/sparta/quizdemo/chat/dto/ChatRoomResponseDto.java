package com.sparta.quizdemo.chat.dto;

import com.sparta.quizdemo.chat.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String roomId;
    private String username;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.roomId = chatRoom.getRoomId();
        this.username = chatRoom.getUser().getUsername();
    }
}
