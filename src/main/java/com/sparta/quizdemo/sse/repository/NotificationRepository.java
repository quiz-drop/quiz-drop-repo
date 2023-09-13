package com.sparta.quizdemo.sse.repository;

import com.sparta.quizdemo.sse.entity.Notification;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.user.id = :userId order by n.id desc")
    List<Notification> findAllByUserId(@Param("userId") Long userId);

    void deleteAllByUserId(Long userId);
}