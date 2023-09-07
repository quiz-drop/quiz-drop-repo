package com.sparta.quizdemo.chat.service;

import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.chat.dto.ChatRoomResponseDto;
import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.chat.repository.ChatMessageRepository;
import com.sparta.quizdemo.chat.repository.ChatRoomRepository;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomService.class);

    public ChatRoomResponseDto createAndEnter(User user) {
        if (chatRoomRepository.findByUser(user).isEmpty()) {
            String roomId = generateUniqueRoomId();
            ChatRoom chatRoom = new ChatRoom(user, roomId);
            chatRoomRepository.save(chatRoom);
            return new ChatRoomResponseDto(chatRoom);
        } else {
            ChatRoom chatRoom = user.getChatRoom();
            return new ChatRoomResponseDto(chatRoom);
        }
    }

    private String generateUniqueRoomId() {
        return UUID.randomUUID().toString();
    }

    public List<ChatRoomResponseDto> getChatRooms(User user) {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return chatRoomRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ChatRoomResponseDto::new)
                .toList();
    }

    public void deleteRoom(User user, String roomId) {
        if (!user.equals(user.getChatRoom().getUser())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        ChatRoom chatRoom = findChatRoom(roomId);
        chatRoomRepository.delete(chatRoom);
    }

    public List<ChatMessageResponseDto> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findAllByRoomIdOrderByCreatedAtAsc (roomId)
                .stream()
                .map(ChatMessageResponseDto::new)
                .toList();
    }

    public void messageSave(ChatMessageRequestDto requestDto) {
        logger.info("Request body: " + requestDto.toString());

        ChatRoom chatRoom = findChatRoom(requestDto.getRoomId());
        User user = userRepository.findByChatRoom(chatRoom).orElseThrow(
                () -> new IllegalArgumentException("user 존재하지 않습니다")
        );
        logger.info("username : " + user);
        String message = requestDto.getMessage(); // 메시지 값 가져오기
        logger.info("Received message: " + message);
        ChatMessage chatMessage = new ChatMessage(requestDto, chatRoom, user);

        chatMessageRepository.save(chatMessage);
    }

    public ChatRoom findChatRoom(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")
        );
    }
}
