package com.sparta.quizdemo.sse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.sse.dto.NotificationResponseDto;
import com.sparta.quizdemo.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    /* MIME TYPE - text/event-stream 형태로 받아야함.
    클라이어트로부터 오는 알림 구독 요청을 받는다.
    로그인한 유저는 SSE 연결
    lAST_EVENT_ID = 이전에 받지 못한 이벤트가 존재하는 경우 [ SSE 시간 만료 혹은 종료 ]
    전달받은 마지막 ID 값을 넘겨 그 이후의 데이터[ 받지 못한 데이터 부터 받을 수 있게 한다 */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                String lastEventId) throws JsonProcessingException {
        SseEmitter sseEmitter = new SseEmitter();

        new Thread(() -> {
            try {
                Thread.sleep(15000);
                sseEmitter.send(SseEmitter.event().name("server-close").data("Server is closing."));
                sseEmitter.complete();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();

        return notificationService.subscribe(userDetails.getUser().getId(), lastEventId);
    }

    /* 알림조회 */
    @GetMapping("/")
    public List<NotificationResponseDto> findAllNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.findAllNotifications(userDetails.getUser());
    }

    /* 알림 전체 삭제 */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto> deleteNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        notificationService.deleteAllByNotifications(userDetails);
        return ResponseEntity.ok().body(new ApiResponseDto("알림 목록 전체삭제 성공", HttpStatus.OK.value()));
    }
}
