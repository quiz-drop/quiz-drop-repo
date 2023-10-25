package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {
    List<ChatRooms> findAllByOrderByCreatedAtDesc();

    Optional<ChatRooms> findByRoomId(String roomId);
}
