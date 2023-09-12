package com.sparta.quizdemo.comment.dto;


import com.sparta.quizdemo.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String productName;
    private String nickname;
    private String content;
    private String commentProductImage;
    private int commentProductScore;
    private int likeCnt;
    private boolean bool;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.productName = comment.getProduct().getProductName();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentProductImage = comment.getCommentProductImage();
        this.commentProductName = comment.getCommentProductName();
        this.commentProductScore = comment.getCommentProductScore();
    }
}
