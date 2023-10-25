package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {
    List<ChatMessages> findAllByChatRoomsRoomIdOrderByCreatedAt(String roomId);
}
