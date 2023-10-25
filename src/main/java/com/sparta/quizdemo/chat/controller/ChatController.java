package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.chat.dto.ChatRoomResponseDto;
import com.sparta.quizdemo.chat.service.ChatService;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    /* chatRoom 생성 */
    @PostMapping("/createAndEnter")
    public ResponseEntity<ChatRoomResponseDto> createAndEnter(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(chatService.createAndEnter(userDetails.getUser()));
    }

    /* chatRoom 전체조회 */
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> getChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.getChatRooms(userDetails.getUser());
    }

    /* chatRoom 삭제 */
    @DeleteMapping("/deleteRoom")
    public ResponseEntity<ApiResponseDto> deleteRoom(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        chatService.deleteRoom(userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("채팅방 삭제 완료", HttpStatus.OK.value()));
    }

    /* chat 저장 */
    @PostMapping("/saveMessages/{roomId}")
    public ResponseEntity<ApiResponseDto> messageSave(@AuthenticationPrincipal UserDetailsImpl userDetails
            , @RequestBody ChatMessageRequestDto requestDto, @PathVariable String roomId) {
        chatService.messageSave(userDetails.getUser(), requestDto, roomId);
        return ResponseEntity.ok().body(new ApiResponseDto("채팅 저장 완료", HttpStatus.OK.value()));
    }

    /* message 조회 */
    @GetMapping("/getMessages/{roomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessagesByRoomId(@PathVariable String roomId) {
        return ResponseEntity.ok().body(chatService.getMessagesByRoomId(roomId));
    }
}