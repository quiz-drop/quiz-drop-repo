package com.sparta.quizdemo.sse.repository;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository{
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>(); /* SSE 연결 정보 저장 */
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>(); /* 이벤트 데이터 캐싱 */

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitterMap.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    /* 특정 사용자의 연결을 추적 */
    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId) {
        return emitterMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId))
                // 해당 userId 로 시작하는 키값을 필터 key, value 리턴
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /* 특정 사용자 이벤트 캐시 */
    @Override
    public Map<String, Object> findAllEventCacheStartWithByUserId(String userId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String id) {
        emitterMap.remove(id);
    }

    @Override
    public void deleteAllEmitterStartWithId(String userId) {
        emitterMap.entrySet().removeIf(entry -> entry.getKey().startsWith(userId));
    }

    @Override
    public void deleteAllEventCacheStartWithId(String userId) {
        eventCache.entrySet().removeIf(entry -> entry.getKey().startsWith(userId));
    }

}
