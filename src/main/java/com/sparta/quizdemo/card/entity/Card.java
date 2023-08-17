package com.sparta.quizdemo.card.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.quizdemo.answer.entity.Answer;
import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cards")
public class Card extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_no")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "quiz_answer", nullable = false)
    private Integer quiz_answer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    public Card(CardRequestDto requestDto) {
        this.level = requestDto.getLevel();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.quiz_answer = requestDto.getQuiz_answer();
    }

    public void update(CardRequestDto cardRequestDto) {
        this.level = cardRequestDto.getLevel();
        this.title = cardRequestDto.getTitle();
        this.content = cardRequestDto.getContent();
        this.quiz_answer = cardRequestDto.getQuiz_answer();
    }
}
