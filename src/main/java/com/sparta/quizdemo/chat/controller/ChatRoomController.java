package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.chat.dto.ChatRoomResponseDto;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.chat.service.ChatRoomService;
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
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    /* chatRoom 생성 */
    @PostMapping("/createAndEnterChatRoom")
    public ResponseEntity<ChatRoomResponseDto> createAndEnterChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChatRoom chatRoom = chatRoomService.createAndEnterChatRoom(userDetails.getUser());
        ChatRoomResponseDto responseDto = new ChatRoomResponseDto(chatRoom.getRoomId(), chatRoom.getUserId(), chatRoom.getUsername());

        return ResponseEntity.ok().body(responseDto);
    }

    /* chatRoom 전체조회 */
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> getChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getAllChatRooms(userDetails.getUser());
    }

    /* chatRoom 삭제 */
    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<ApiResponseDto> deleteRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable String roomId) {
        chatRoomService.deleteRoom(userDetails.getUser(), roomId);
        return ResponseEntity.ok().body(new ApiResponseDto("채팅방 삭제 완료", HttpStatus.OK.value()));
    }
}