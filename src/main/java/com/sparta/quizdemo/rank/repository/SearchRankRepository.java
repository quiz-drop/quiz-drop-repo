package com.sparta.quizdemo.rank.repository;

import com.sparta.quizdemo.rank.entity.SearchRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRankRepository extends JpaRepository<SearchRank, Long> {
}
