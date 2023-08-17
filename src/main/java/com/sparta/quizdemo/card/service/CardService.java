package com.sparta.quizdemo.card.service;

import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.card.dto.CardResponseDto;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardService {
    /**
     * 퀴즈카드 생성 API
     * @param requestDto 셍성하는 퀴즈카드 정보
     */
    ResponseEntity<CardResponseDto> createCard(CardRequestDto requestDto);

    /**
     * 퀴즈카드 목록 조회 API
     */
    ResponseEntity<List<CardResponseDto>> getCards();

    /**
     * 퀴즈카드 선택 조회 API
     * @param cardNo 선택한 퀴즈카드 id
     */
    ResponseEntity<CardResponseDto> getCard(Long cardNo);

    /**
     * 퀴즈카드 정보 수정 API
     * @param cardNo 선택한 퀴즈카드 id
     * @param cardRequestDto 수정하는 퀴즈카드 정보
     */
    ResponseEntity<ApiResponseDto> updateCard(Long cardNo, CardRequestDto cardRequestDto);

    /**
     * 퀴즈카드 정보 삭제 API
     * @param cardNo 선택한 퀴즈카드 id
     */
    ResponseEntity<ApiResponseDto> deleteCard(Long cardNo);
}
