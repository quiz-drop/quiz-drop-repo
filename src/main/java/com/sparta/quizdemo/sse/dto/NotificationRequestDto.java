package com.sparta.quizdemo.sse.dto;

import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private Long notificationId;
    private User user;
    private NotificationType notificationType;
}
