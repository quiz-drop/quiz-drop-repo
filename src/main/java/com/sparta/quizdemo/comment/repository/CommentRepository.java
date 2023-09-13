package com.sparta.quizdemo.comment.repository;


import com.sparta.quizdemo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long productNo);
}
