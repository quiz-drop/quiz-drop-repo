package com.sparta.quizdemo.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private Integer level;
    @NotBlank(message = "필수 입력 값입니다.")
    private String title;
    @NotBlank(message = "필수 입력 값입니다.")
    private String content;
    private Integer quiz_answer;
}
