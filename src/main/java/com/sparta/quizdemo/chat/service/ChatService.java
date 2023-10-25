package com.sparta.quizdemo.chat.service;

import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.chat.dto.ChatMessageResponseDto;
import com.sparta.quizdemo.chat.dto.ChatRoomResponseDto;
import com.sparta.quizdemo.chat.entity.ChatMessages;
import com.sparta.quizdemo.chat.entity.ChatRooms;
import com.sparta.quizdemo.chat.entity.UserChatRooms;
import com.sparta.quizdemo.chat.repository.ChatMessagesRepository;
import com.sparta.quizdemo.chat.repository.ChatRoomsRepository;
import com.sparta.quizdemo.chat.repository.UserChatRoomsRepository;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRoomsRepository chatRoomsRepository;
    private final ChatMessagesRepository chatMessagesRepository;
    private final UserChatRoomsRepository userChatRoomsRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public ChatRoomResponseDto createAndEnter(User user) {
        Optional<UserChatRooms> existingUserChatRooms = userChatRoomsRepository.findByUser(user);

        if (existingUserChatRooms.isEmpty()) {
            String roomId = UUID.randomUUID().toString();

            // 채팅방 생성
            ChatRooms chatRooms = ChatRooms.builder()
                    .roomId(roomId)
                    .build();
            chatRoomsRepository.save(chatRooms);

            // 관리자 추가
            List<User> adminUsers = userRepository.findByRole(UserRoleEnum.ADMIN);
            for (User adminUser : adminUsers) {
                UserChatRooms userChatRooms = UserChatRooms.builder()
                        .user(adminUser)
                        .role("ADMIN")
                        .chatRooms(chatRooms)
                        .build();
                userChatRoomsRepository.save(userChatRooms);
            }

            // 현재 사용자 추가
            UserChatRooms userChatRooms = UserChatRooms.builder()
                    .user(user)
                    .role("USER")
                    .chatRooms(chatRooms)
                    .build();
            userChatRoomsRepository.save(userChatRooms);

            return new ChatRoomResponseDto(chatRooms);
        } else {
            return new ChatRoomResponseDto(existingUserChatRooms.get().getChatRooms());
        }
    }

    public List<ChatRoomResponseDto> getChatRooms(User user) {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return chatRoomsRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ChatRoomResponseDto::new)
                .toList();
    }

    @CacheEvict(value = "Messages", allEntries = true, cacheManager = "cacheManager")
    public void deleteRoom(User user) {
        UserChatRooms userChatRooms = findByChatRoom(user);
        ChatRooms chatRooms = userChatRooms.getChatRooms();

        if (!user.getId().equals(userChatRooms.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        chatRoomsRepository.delete(chatRooms);
        userChatRoomsRepository.delete(userChatRooms);
    }

    public void messageSave(User user, ChatMessageRequestDto requestDto, String roomId) {
        logger.info("username : " + user.getUsername());
        ChatRooms chatRooms = chatRoomsRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("채팅 방을 찾을 수 없습니다."));

        String message = requestDto.getMessage();
        logger.info("message : " + message);

        ChatMessages chatMessages = ChatMessages.builder()
                .roomId(roomId)
                .message(message)
                .user(user)
                .chatRooms(chatRooms)
                .build();
        chatMessagesRepository.save(chatMessages);
    }

    @Cacheable(value = "Messages", key = "#roomId", cacheManager = "cacheManager")
    public List<ChatMessageResponseDto> getMessagesByRoomId(String roomId) {
        List<ChatMessages> chatMessages = chatMessagesRepository.findAllByChatRoomsRoomIdOrderByCreatedAt(roomId);
        List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();

        for (ChatMessages chatMessage : chatMessages) {
            chatMessageResponseDtoList.add(new ChatMessageResponseDto(chatMessage));
        }
        return chatMessageResponseDtoList;
    }

    public UserChatRooms findByChatRoom(User user) {
        return userChatRoomsRepository.findByUser(user).orElseThrow(
                () -> new IllegalArgumentException("방이 존재하지 않습니다.")
        );
    }
}