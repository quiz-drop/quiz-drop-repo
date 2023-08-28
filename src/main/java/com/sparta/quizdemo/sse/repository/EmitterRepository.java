package com.sparta.quizdemo.sse.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    /**
     * emitter 저장
     * @param emitterId
     * @param sseEmitter
     * @return
     */
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    /**
     * 이벤트 저장
     * @param eventCacheId
     * @param event
     */
    void saveEventCache(String eventCacheId, Object event);

    /**
     * 해당 회원과 관련된 모든 emitter 찾기
     * @param userId
     * @return
     */
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

    /**
     * 해당 회원과 관련된 모든 이벤트를 찾는다. - 브라우저 당 여러개 연결이 가능하기에 여러 Emitter가 존재
     * @param userId
     * @return
     */
    Map<String,Object> findAllEventCacheStartWithByUserId(String userId);

    /**
     * 해당 회원과 관련된 emitter 삭제
     * @param id
     */
    void deleteById(String id);

    /**
     * 해당 회원과 관련된 모든 emitter 삭제
     * @param userId
     */
    void deleteAllEmitterStartWithId(String userId);

    /**
     * 해당 회원과 관련된 모든 이벤트 삭제
     * @param userId
     */
    void deleteAllEventCacheStartWithId(String userId);
}
