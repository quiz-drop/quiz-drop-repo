package com.sparta.quizdemo.sse.dto;

import com.sparta.quizdemo.common.entity.User;
import com.sparta.quizdemo.sse.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private User receiver;
    private NotificationType notificationType;
    private String content;
    private String url;
}
