package com.sparta.quizdemo.order.repository;

import com.sparta.quizdemo.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findAllByOrderByCreatedAtAsc();

    List<Order> findAllByUserIdOrderByCreatedAtDesc(Long id);

    List<Order> findAllByUserIdOrderByCreatedAtAsc(Long id);
}
