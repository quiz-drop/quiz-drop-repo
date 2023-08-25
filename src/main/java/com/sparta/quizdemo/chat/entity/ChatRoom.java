package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.common.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    private String roomId;
    private String username;

    public ChatRoom(String username, String roomId) {
        this.roomId = roomId;
        this.username = username;
    }

    public static ChatRoom create(User user) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.username = user.getUsername();
        return chatRoom;
    }
}