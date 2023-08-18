package com.sparta.quizdemo.cart.repository;

import com.sparta.quizdemo.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(Long id);

    List<CartItem> findAllByUserId(Long id);
}
