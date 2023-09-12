package com.sparta.quizdemo.sse;

import com.sparta.quizdemo.sse.dto.NotificationRequestDto;
import com.sparta.quizdemo.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationRequestDto requestDto) {
        notificationService.send(requestDto.getUser(), requestDto.getNotificationType(), requestDto.getNotificationType().getMessage(), LocalDateTime.now());
    }
}
