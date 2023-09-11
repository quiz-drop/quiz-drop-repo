package com.sparta.quizdemo.sse.service;

import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.sse.dto.NotificationCountDto;
import com.sparta.quizdemo.sse.dto.NotificationResponseDto;
import com.sparta.quizdemo.sse.entity.Notification;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.sse.repository.EmitterRepository;
import com.sparta.quizdemo.sse.repository.EmitterRepositoryImpl;
import com.sparta.quizdemo.sse.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(Long userId, String lastEventId) {
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
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    // 유효시간이 다 지난다면 503 에러가 발생하기 때문에 더미데이터를 발행
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
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
    public void send(User receiver, NotificationType notificationType, String content) {
        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, content));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationResponseDto.create(notification));
                }
        );
    }

    @Async
    public void sendNotification(User user, NotificationType notificationType, String content) {
        Notification notification = notificationRepository.save(createNotification(user, notificationType, content));
        String userId = String.valueOf(user.getId());

        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);

        emitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, notification);
            sendNotification(emitter, NotificationResponseDto.create(notification));
        });
    }

    private void sendNotification(SseEmitter emitter, NotificationResponseDto notification) {
        try {
            emitter.send(notification);
        } catch (IOException e) {
        }
    }

    private Notification createNotification(User receiver, NotificationType notificationType, String content) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .isRead(false) // 현재 읽음상태
                .build();
    }

    public List<NotificationResponseDto> findAllNotifications(User user) {
        List<Notification> notifications = notificationRepository.findAllByUserId(user.getId());
        return notifications.stream()
                .map(NotificationResponseDto::create)
                .collect(Collectors.toList());
    }


    public NotificationCountDto countUnReadNotifications(Long userId) {
        //유저의 알람리스트에서 ->isRead(false)인 갯수를 측정 ,
        Long count = notificationRepository.countUnReadNotifications(userId);
        return NotificationCountDto.builder()
                .count(count)
                .build();
    }

    @Transactional
    public void readNotification(Long notificationId) {
        //알림을 받은 사람의 id 와 알림의 id 를 받아와서 해당 알림을 찾는다.
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        Notification checkNotification = notification.orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다."));
        checkNotification.read(); // 읽음처리
    }

    @Transactional
    public void deleteAllByNotifications(UserDetailsImpl userDetails) {
        Long receiverId = userDetails.getUser().getId();
        notificationRepository.deleteAllByReceiverId(receiverId);

    }

    @Transactional
    public void deleteByNotifications(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
