package com.sparta.quizdemo.sse.repository;

import com.sparta.quizdemo.sse.entity.Notification;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

class EmitterRepositoryImplTest {

    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Test
    @DisplayName("새로운 Emitter 추가")
    void save() throws Exception {
        // given
        Long userId = 1L;
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // when, then
        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("수신한 이벤트 캐시에 저장")
    void saveEventCache() throws Exception {
        // given
        Long userId = 1L;

        String eventCacheId = userId + "_" + System.currentTimeMillis();
        Notification notification = new Notification(new User(userId), NotificationType.ORDER_COMPLETED);

        // Then
        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, notification));
    }

    @Test
    @DisplayName("해당 회원이 접속한 모든 Emitter 찾기")
    void findAllEmitterStartWithByUserId() throws Exception {
        // given
        Long userId = 1L;
        String emitterId1 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        // when
        Map<String, SseEmitter> ActualResult = emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId));

        // then
        Assertions.assertEquals(2, ActualResult.size());
    }

    @Test
    @DisplayName("해당 회원에게 수신된 이벤트 캐시에서 모두 찾기")
    void findAllEventCacheStartWithByUserId() throws Exception {
        //given
        Long userId = 1L;

        String eventCacheId1 = userId + "_" + System.currentTimeMillis();
        Notification notification1 = new Notification(new User(userId), NotificationType.ORDER_COMPLETED);
        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 = userId + "_" + System.currentTimeMillis();
        Notification notification2 = new Notification(new User(userId), NotificationType.ORDER_CANCELLED);
        emitterRepository.saveEventCache(eventCacheId2, notification2);

        Map<String, Object> ActualResult = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));

        //then
        Assertions.assertEquals(2, ActualResult.size());
    }

    @Test
    @DisplayName("ID를 통해 Emitter를 Repository에서 제거")
    void deleteById() throws Exception {
        //given
        Long userId = 1L;
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        //when
        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByUserId(emitterId).size());
    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거")
    void deleteAllEmitterStartWithId() throws Exception {
        //given
        Long userId = 1L;
        String emitterId1 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        //when
        emitterRepository.deleteAllEmitterStartWithId(String.valueOf(userId));

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId)).size());
    }

    @Test
    @DisplayName("수신한 모든 이벤트 제거")
    void deleteAllEventCacheStartWithId() throws Exception {
        //given
        Long userId = 1L;
        String eventCacheId1 = userId + "_" + System.currentTimeMillis();
        Notification notification1 = new Notification(new User(1L), NotificationType.ORDER_COMPLETED);
        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 = userId + "_" + System.currentTimeMillis();
        Notification notification2 = new Notification(new User(1L), NotificationType.ORDER_CANCELLED);
        emitterRepository.saveEventCache(eventCacheId2, notification2);

        //when
        emitterRepository.deleteAllEventCacheStartWithId(String.valueOf(userId));

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId)).size());
    }
}