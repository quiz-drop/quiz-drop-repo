package com.sparta.quizdemo.answer.entity;

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

//    @ManyToOne
//    @JoinColumn(name = "user_no")
//    private User user;

    @ManyToOne
    @JoinColumn(name = "card_no")
    private Card card;
}
