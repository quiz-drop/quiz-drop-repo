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
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/")   //댓글 작성
    public ResponseEntity<ApiResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, userDetails.getUser());
    }

    @GetMapping("/")   //댓글 전체 조회
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        return commentService.getComments();
    }

    @PutMapping("/{comment_id}")    //댓글 수정
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(comment_id, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{comment_id}") //댓글 삭제
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable long comment_id) {
        return commentService.deleteComment(comment_id);
    }


    // 댓글 좋아요
    @PostMapping("/likes/{comment_id}")
    public ResponseEntity<ApiResponseDto> likeComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return commentService.create_CommentLike(comment_id, user);
    }

    // 댓글 좋아요 삭제
    @DeleteMapping("/likes/{comment_id}")
    public ResponseEntity<ApiResponseDto> deleteLikeComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return commentService.delete_CommentLike(comment_id, user);
    }
}
