package com.sparta.quizdemo.sse.dto;

import com.sparta.quizdemo.sse.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createAt;

    public static NotificationResponseDto create(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getContent(),
                notification.getCreatedAt());
    }
}