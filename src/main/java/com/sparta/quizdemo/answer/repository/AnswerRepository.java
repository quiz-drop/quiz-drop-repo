package com.sparta.quizdemo.answer.repository;

import com.sparta.quizdemo.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
