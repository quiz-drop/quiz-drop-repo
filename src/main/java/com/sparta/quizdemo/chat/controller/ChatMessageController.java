package com.sparta.quizdemo.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.chat.service.ChatMessageService;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    /* 채팅 메시지 저장 */
    @PostMapping("/saveMessages/{roomId}")
    public ResponseEntity<String> saveChatMessage(@RequestBody ChatMessageRequestDto requestDto,
                                                  @PathVariable String roomId) {
        try {
            chatMessageService.messageSave(requestDto, roomId);
            return ResponseEntity.ok("채팅 메시지가 성공적으로 저장되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /* 채팅 메시지 조회 */
    @GetMapping("/getMessages/{roomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getChatMessages(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String roomId) throws JsonProcessingException {
        List<ChatMessageResponseDto> chatMessages = chatMessageService.getChatMessages(userDetails.getUser(), roomId);
        return ResponseEntity.ok(chatMessages);
    }
}