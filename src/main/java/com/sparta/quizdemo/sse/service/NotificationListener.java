package com.sparta.quizdemo.sse.service;

import com.sparta.quizdemo.sse.dto.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationRequestDto requestDto) {
        notificationService.send(requestDto.getUser(), requestDto.getNotificationType(), requestDto.getNotificationType().getMessage());
    }
}
