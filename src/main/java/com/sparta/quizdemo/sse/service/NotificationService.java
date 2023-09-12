package com.sparta.quizdemo.sse.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.sse.dto.NotificationRequestDto;
import com.sparta.quizdemo.sse.dto.NotificationResponseDto;
import com.sparta.quizdemo.sse.entity.Notification;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.sse.repository.EmitterRepository;
import com.sparta.quizdemo.sse.repository.EmitterRepositoryImpl;
import com.sparta.quizdemo.sse.repository.NotificationRepository;
import com.sparta.quizdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(Long userId, String lastEventId) throws JsonProcessingException {
        //emitter 고유 값 생성
        String emitterId = makeTimeIncludeId(userId);

        Long timeout = 60L * 1000L * 60L;

        // 생성된 emiiterId를 기반으로 emitter를 저장
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));

        //emitter 완료 및 시간 만료시 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(userId);

        // 수 많은 이벤트 들을 구분하기 위해 이벤트 ID에 시간을 통해 구분을 해줌
        String message = new ObjectMapper().writeValueAsString(Collections.singletonMap("message", "EventStream Created. [userId=" + userId + "]"));
        sendNotification(emitter, eventId, emitterId, message);

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    // Last - event - id 가 존재한다는 것은 받지 못한 데이터가 있다는 것이다.
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    // 받지못한 데이터가 있다면 last - event - id를 기준으로 그 뒤의 데이터를 추출해 알림을 보내주면 된다.
    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Async
    public void send(User user, NotificationType notificationType, String message, LocalDateTime createdAt) {
        Notification notification = notificationRepository.save(createNotification(user, notificationType));

        String userId = String.valueOf(user.getId());
        String eventId = userId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationResponseDto.create(notification));
                }
        );
    }

    private Notification createNotification(User user, NotificationType notificationType) {
        return Notification.builder()
                .user(user)
                .notificationType(notificationType)
                .build();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    public List<NotificationResponseDto> findAllNotifications(User user) {
        List<Notification> notifications = notificationRepository.findAllByUserId(user.getId());
        return notifications.stream()
                .map(NotificationResponseDto::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllByNotifications(UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        notificationRepository.deleteAllByUserId(userId);
    }
}
