package com.sparta.quizdemo.chat.service;

import com.sparta.quizdemo.chat.dto.ChatRoomResponseDto;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ChatRoomServiceTest {
    @Autowired
    ChatRoomService chatRoomService;

    @Test
    @DisplayName("채팅 생성")
    void createAndEnterChatRoom() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setRole(UserRoleEnum.USER);

        // when
        ChatRoom result = chatRoomService.createAndEnterChatRoom(user);

        // then
        assertNotNull(result);
        assertEquals(ChatRoom.class, result.getClass());
    }

    @Test
    @DisplayName("채팅방 전체 조회")
    void getAllChatRooms() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setRole(UserRoleEnum.ADMIN);

        // when
        List<ChatRoomResponseDto> result = chatRoomService.getAllChatRooms(user);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("채팅방 삭제")
    void testDeleteRoom() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setRole(UserRoleEnum.USER); // user 권한을 가진 사용자로 설정합니다.

        // 채팅방 생성
        ChatRoom chatRoom = chatRoomService.createAndEnterChatRoom(user);
        String roomId = chatRoom.getRoomId();

        // when
        chatRoomService.deleteRoom(user, roomId);
    }

}