package com.sparta.quizdemo.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.quizdemo.answer.dto.AnswerRequestDto;
import com.sparta.quizdemo.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_no")
    private Long id;

    @Column(name = "user_answer", nullable = false)
    private Integer user_answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_no")
    private Card card;

    public Answer(AnswerRequestDto answerRequestDto, User user, Card card) {
        this.user_answer = answerRequestDto.getUser_answer();
        this.user = user;
        this.card = card;
    }
}
