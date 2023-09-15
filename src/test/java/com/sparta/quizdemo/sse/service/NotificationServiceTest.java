package com.sparta.quizdemo.sse.service;

import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;

    @Test
    @DisplayName("알림 구독")
    void subscribe() throws Exception{
        // given
        Long userId = 1L;
        String lastEventId = "lastEventId";

        // when
        SseEmitter result = notificationService.subscribe(userId, lastEventId);

        // then
        assertNotNull(result);
        assertEquals(SseEmitter.class, result.getClass());
    }

    @Test
    @DisplayName("알림 전송")
    void send() throws Exception{
        // given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        NotificationType notificationType = NotificationType.ORDER_COMPLETED;
        String message = "Test message";

        // when
        notificationService.send(user, notificationType, message);
    }
}