package com.sparta.quizdemo.order.repository;

import com.sparta.quizdemo.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
