package com.sparta.quizdemo.order.repository;

import com.sparta.quizdemo.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByProductId(Long productNo);
}
