package com.sparta.quizdemo.backoffice.repository;

import com.sparta.quizdemo.backoffice.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BackofficeRepository extends JpaRepository<Visitor, Long> {
    boolean existsByVisitorIPAndDate(String userIp, LocalDate date);
}
