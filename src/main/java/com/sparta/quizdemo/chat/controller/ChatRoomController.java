package com.sparta.quizdemo.chat.controller;

import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.chat.service.ChatRoomService;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /* 채팅방 생성 및 입장 */
    @PostMapping("/createAndEnterChatRoom")
    public ResponseEntity<ChatRoom> createAndEnterChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChatRoom chatRoom = chatRoomService.createAndEnterChatRoom(userDetails.getUser());
        return ResponseEntity.ok(chatRoom);
    }

    /* 채팅방 조회 */
    @GetMapping("/rooms")
    @Secured("ROLE_ADMIN")
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.findAllRooms();
    }

    /* 채팅방 단권 조회 */
    @GetMapping("/rooms/{username}")
    public ChatRoom getChatRoomByUser(@PathVariable String username) {
        return chatRoomService.findRoomByUser(username);
    }

    /* 채팅방 나가기 */
    @DeleteMapping("/rooms/{username}")
    public void deleteChatRoom(@PathVariable String username) {
        chatRoomService.deleteChatRoom(username);
    }

}