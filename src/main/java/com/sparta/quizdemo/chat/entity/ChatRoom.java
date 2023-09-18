package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom{
    private String roomId;
    private String userId;
    private String username;

    public ChatRoom(String roomId, String userId, String username) {
        this.roomId = roomId;
        this.userId = userId;
        this.username = username;
    }

    public static ChatRoom create(User user) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = "user_" + user.getId();
        chatRoom.userId = user.getId().toString();
        chatRoom.username = user.getUsername();
        return chatRoom;
    }
}