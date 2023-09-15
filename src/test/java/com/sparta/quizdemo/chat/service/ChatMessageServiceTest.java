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

        // then
        // 여기서는 messageSave 메서드가 예외를 발생시키지 않는 것을 확인합니다.
        // messageSave 메서드의 실제 결과를 검증하려면 추가적인 모킹이나 설정이 필요할 수 있습니다.
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