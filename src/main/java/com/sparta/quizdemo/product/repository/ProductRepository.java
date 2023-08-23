package com.sparta.quizdemo.product.repository;

import com.sparta.quizdemo.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);

    List<Product> findAllByOrderByCreatedAtAsc();
}
