package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    private String roomId;
    private String username;
    private String message;
    private Long timestamp;
    private String formattedTimestamp;

    public ChatMessage(ChatMessageRequestDto requestDto) {
        this.roomId = requestDto.getRoomId();
        this.username = requestDto.getUsername();
        this.message = requestDto.getMessage();
        this.timestamp = System.currentTimeMillis();

        // 타임스탬프를 날짜와 시간으로 변환
        Instant instant = Instant.ofEpochMilli(this.timestamp);
        this.formattedTimestamp = DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}
