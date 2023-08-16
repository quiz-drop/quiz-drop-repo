package com.sparta.quizdemo.card.dto;

import lombok.Getter;

@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private Integer level;
    private String content;
    private Integer quiz_answer;
}
