package com.sparta.quizdemo.comment.entity;

import com.sparta.quizdemo.comment.dto.CommentRequestDto;
import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "comment_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Comment(Product product, CommentRequestDto commentRequestDto, User user) {
        this.product = product;
        this.content = commentRequestDto.getContent();
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getContent();
        this.user = user;
    }
}
