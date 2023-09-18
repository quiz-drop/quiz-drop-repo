package com.sparta.quizdemo.chat.service;

import com.sparta.quizdemo.chat.dto.ChatRoomResponseDto;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.common.exception.CustomException;
import com.sparta.quizdemo.common.exception.ErrorCode;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    /* 채팅 생성 */
    public ChatRoom createAndEnterChatRoom(User user) {
        if (!checkRole(user.getRole())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        User findUser = findByUser(user.getId());

        String roomId = "user_" + findUser.getId();
        Boolean existingChatRoom = hasChatRoom(roomId);

        if (existingChatRoom) {
            log.info("채팅방 입장");
            log.info(roomId);
            return new ChatRoom(roomId, findUser.getId().toString(), findUser.getUsername());
        } else {
            // 채팅방이 존재하지 않는 경우
            ChatRoom chatRoom = ChatRoom.create(findUser);

            Map<String, String> roomInfo = new HashMap<>();
            roomInfo.put("userId", chatRoom.getUserId());
            roomInfo.put("username", chatRoom.getUsername());

            redisTemplate.opsForHash().putAll(roomId, roomInfo);
            log.info("채팅방 생성");
            log.info(roomId);
            return chatRoom;
        }
    }

    /* 채팅방 전체 조회 */
    public List<ChatRoomResponseDto> getAllChatRooms(User user) {
        if (checkRole(user.getRole())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        List<ChatRoomResponseDto> chatRooms = new ArrayList<>();

        ScanOptions options = ScanOptions.scanOptions().match("user_*").count(100).build();
        Cursor<String> cursor = redisTemplate.scan(options);

        while (cursor.hasNext()) {
            String chatRoomId = cursor.next();
            Map<Object, Object> roomInfo = redisTemplate.opsForHash().entries(chatRoomId);
            ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoomId, (String) roomInfo.get("userId"), (String) roomInfo.get("username"));
            chatRooms.add(chatRoomResponseDto);
        }

        cursor.close();
        return chatRooms;
    }

    /* 채팅방 삭제 */
    public void deleteRoom(User user, String roomId) {
        findByUser(user.getId());

        if (!checkRole(user.getRole())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        if (!hasChatRoom(roomId)) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }

        try {
            redisTemplate.delete(roomId);
            String redisKey = "messages:" + roomId;
            redisTemplate.delete(redisKey);
            redisTemplate.opsForList().remove("chatRooms", 0, roomId);

        } catch (DataAccessException ex) {
            Throwable targetException = ex.getCause();
            if (targetException instanceof NoSuchElementException) {
                log.info("해당 키가 존재하지 않습니다.");
            } else if (targetException instanceof RedisConnectionFailureException) {
                log.info("Redis 연결이 실패했습니다.");
            } else {
                log.info("예외 발생: " + ex.getMessage());
            }
        }
    }

    /* 채팅방 존재 여부 */
    public Boolean hasChatRoom(String roomId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(roomId));
    }

    /* 로그인 여부 */
    public User findByUser(Long userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.UNAUTHORIZED)
        );
    }

    /* 권한 확인 */
    public Boolean checkRole(UserRoleEnum role) {
        return role.equals(UserRoleEnum.USER);
    }
}