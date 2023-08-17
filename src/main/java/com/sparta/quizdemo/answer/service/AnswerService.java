package com.sparta.quizdemo.answer.service;

import com.sparta.quizdemo.answer.dto.AnswerRequestDto;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface AnswerService {
    /**
     * 퀴즈카드 생성 API
     * @param cardNo 유저 정답을 등록하는 퀴즈카드 번호
     * @param answerRequestDto 등록하려는 유저 정답
     * @param user 정답을 등록하는 유저 정보
     */
    ResponseEntity<ApiResponseDto> postAnswer(Long cardNo, AnswerRequestDto answerRequestDto, User user);
}
