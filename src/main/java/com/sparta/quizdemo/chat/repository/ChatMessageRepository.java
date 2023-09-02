package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import com.sparta.quizdemo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ChatMessageRepository  extends JpaRepository<ChatMessage, Long> {
    Collection<ChatMessage> findAllByRoomIdOrderByCreatedAtAsc(String roomId);
}
