package com.sparta.quizdemo.cart.repository;

import com.sparta.quizdemo.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long id);

    List<CartItem> findAllByCartId(Long id);

    List<CartItem> findAllByProductId(Long productNo);

    Optional<CartItem> findByIdAndCartId(Long cartItemNo, Long id);

    List<CartItem> findByProductIdAndCartId(Long id, Long id1);
}
