package com.sparta.quizdemo.sse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class NotificationContent {

    @Column(nullable = false)
    private String content;

    public NotificationContent(String content) {
        if (isNotValidNotificationContent(content)) {
            throw new IllegalArgumentException("유효하지 않는 알림입니다.");
        }
        this.content = content;
    }

    private boolean isNotValidNotificationContent(String content) {
        return Objects.isNull(content) || content.isEmpty();
    }
}
