package com.sparta.quizdemo.common.entity;

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

    public enum MessageType {
        ENTER, TALK
    }
}
