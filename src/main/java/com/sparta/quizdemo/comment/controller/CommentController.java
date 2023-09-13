package com.sparta.quizdemo.comment.controller;


import com.sparta.quizdemo.comment.dto.CommentRequestDto;
import com.sparta.quizdemo.comment.dto.CommentResponseDto;
import com.sparta.quizdemo.comment.service.CommentService;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/product/{productNo}/comment")   //댓글 작성
    public ResponseEntity<ApiResponseDto> createComment(@PathVariable Long productNo, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(productNo, commentRequestDto, userDetails.getUser());
    }

    @GetMapping("/comments")   //댓글 전체 조회
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        return commentService.getComments();
    }

    @DeleteMapping("/{commentNo}") //댓글 삭제
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete_Comment(commentNo, userDetails.getUser());
    }
}
