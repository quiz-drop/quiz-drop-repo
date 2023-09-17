package com.sparta.quizdemo.sse.repository;

import com.sparta.quizdemo.sse.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}