package com.sparta.quizdemo.card.dto;

import lombok.Getter;

@Getter
public class CardRequestDto {
    private Integer level;
    private String title;
    private String content;
    private Integer quiz_answer;
}
