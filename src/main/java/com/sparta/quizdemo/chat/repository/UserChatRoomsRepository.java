package com.sparta.quizdemo.chat.repository;

import com.sparta.quizdemo.chat.entity.UserChatRooms;
import com.sparta.quizdemo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserChatRoomsRepository extends JpaRepository<UserChatRooms, Long> {
    Optional<UserChatRooms> findByUser(User user);
}
