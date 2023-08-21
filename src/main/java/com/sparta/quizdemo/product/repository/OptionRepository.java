package com.sparta.quizdemo.product.repository;

import com.sparta.quizdemo.product.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByOptionName(String optionName);

    List<Option> findAllByCategory(String categoryName);
}
