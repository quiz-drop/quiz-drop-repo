package com.sparta.quizdemo.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom implements Serializable {

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