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
import org.springframework.data.redis.core.RedisTemplate;
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
            return new ChatRoom(roomId, findUser.getUsername());
        } else {
            // 채팅방이 존재하지 않는 경우
            ChatRoom chatRoom = ChatRoom.create(findUser);

            Map<String, String> roomInfo = new HashMap<>();
            roomInfo.put("username", chatRoom.getUsername());

            redisTemplate.opsForHash().putAll(roomId, roomInfo);

            // 채팅방 목록에 roomId를 추가
            redisTemplate.opsForList().leftPush("chatRooms", roomId);

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

        // Redis에서 채팅방 목록을 가져옵니다
        List<String> chatRoomIds = redisTemplate.opsForList().range("chatRooms", 0, -1);

        assert chatRoomIds != null;
        for (String chatRoomId : chatRoomIds) {
            // Redis에서 해당 채팅방 정보를 조회합니다
            Map<Object, Object> roomInfo = redisTemplate.opsForHash().entries(chatRoomId);

            // ChatRoomResponseDto 객체로 변환
            ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto();
            chatRoomResponseDto.setRoomId(chatRoomId);
            chatRoomResponseDto.setUsername((String) roomInfo.get("username"));

            chatRooms.add(chatRoomResponseDto);
        }

        return chatRooms;
    }

    /* 채팅방 삭제 */
    public void deleteRoom(User user, String roomId) {
        if (!checkRole(user.getRole())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        User findUser = findByUser(user.getId());

        if (!hasChatRoom(roomId)) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }

        try {
            redisTemplate.delete(roomId);
            String redisKey = roomId + ":messages";
            redisTemplate.delete(redisKey);
            redisTemplate.opsForList().remove("chatRooms", 0, roomId);
        } catch (DataAccessException ex) {
            Throwable targetException = ex.getCause();
            if (targetException instanceof NoSuchElementException) {
                // 해당 키가 없는 경우 처리
                System.out.println("해당 키가 존재하지 않습니다.");
            } else if (targetException instanceof RedisConnectionFailureException) {
                // Redis 연결 실패 경우 처리
                System.out.println("Redis 연결이 실패했습니다.");
            } else {
                System.out.println("예외 발생: " + ex.getMessage());
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