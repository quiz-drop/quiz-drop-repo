package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
