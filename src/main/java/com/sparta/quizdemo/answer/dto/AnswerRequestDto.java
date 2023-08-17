package com.sparta.quizdemo.answer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AnswerRequestDto {
    @NotBlank
    private Integer user_answer;
}
