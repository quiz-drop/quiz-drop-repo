package com.sparta.quizdemo.backoffice.repository;

import com.sparta.quizdemo.backoffice.entity.BlackEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackEmailRepository extends JpaRepository<BlackEmail, Long> {
}
