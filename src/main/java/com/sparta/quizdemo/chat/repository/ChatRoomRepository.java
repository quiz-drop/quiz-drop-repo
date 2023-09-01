package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByOrderByCreatedAtDesc();

    Optional<ChatRoom> findByRoomId(String roomId);

    Optional<ChatRoom> findByUser(User user);
}
