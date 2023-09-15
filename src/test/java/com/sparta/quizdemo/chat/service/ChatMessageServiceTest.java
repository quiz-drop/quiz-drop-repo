package com.sparta.quizdemo.chat.service;

import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ChatMessageServiceTest {
    @Autowired
    ChatMessageService chatMessageService;

    @Test
    @DisplayName("메시지 저장")
    void messageSave() throws Exception {
        // given
        ChatMessageRequestDto requestDto = new ChatMessageRequestDto();
        requestDto.setUsername("testUser");
        String roomId = "testRoom";

        // when
        chatMessageService.messageSave(requestDto, roomId);
    }

    @Test
    @DisplayName("메시지 조회")
    void getChatMessages() throws Exception {
        // given
        User user = new User();
        user.setUsername("testUser");
        String roomId = "testRoom";

        // when
        List<ChatMessageResponseDto> result = chatMessageService.getChatMessages(user, roomId);

        // then
        assertNotNull(result);
    }
}