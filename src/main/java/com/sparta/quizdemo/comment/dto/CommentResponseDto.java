package com.sparta.quizdemo.comment.dto;


import com.sparta.quizdemo.comment.entity.Comment;
import com.sparta.quizdemo.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String commentProductName;
    private String nickname;
    private String content;
    private String commentProductImage;
    private Integer commentProductScore;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentProductName = comment.getProduct().getProductName();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.commentProductImage = comment.getProduct().getProductImage();
        this.commentProductScore = comment.getProduct().getProductScore();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
