package com.sparta.quizdemo.chat.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sparta.quizdemo.chat.entity.ChatMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChatMessageResponseDto {
    private String roomId;
    private String username;
    private String message;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public ChatMessageResponseDto(ChatMessages chatMessages) {
        this.roomId = chatMessages.getChatRooms().getRoomId();
        this.username = chatMessages.getUser().getUsername();
        this.message = chatMessages.getMessage();
        this.createdAt = chatMessages.getCreatedAt();
    }
}