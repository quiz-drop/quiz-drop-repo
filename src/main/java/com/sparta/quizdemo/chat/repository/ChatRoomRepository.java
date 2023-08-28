package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
