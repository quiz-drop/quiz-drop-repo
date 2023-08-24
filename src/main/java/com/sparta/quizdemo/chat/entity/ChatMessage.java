package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.user.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    private String messageId;
    private MessageType type;
    private String roomId;
    private String message;
    private String sender;
    private UserRoleEnum role;

    public enum MessageType {
        ENTER, TALK
    }
}
