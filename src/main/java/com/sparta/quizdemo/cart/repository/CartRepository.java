package com.sparta.quizdemo.cart.repository;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long id);

    Optional<Cart> findByUser(User user);
}
