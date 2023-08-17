package com.sparta.quizdemo.card.repository;

import com.sparta.quizdemo.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByOrderByCreatedAtDesc();
}
