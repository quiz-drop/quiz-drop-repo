package com.sparta.quizdemo.card.service;

import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.card.dto.CardResponseDto;

public interface CardService {
    /**
     * 퀴즈카드 생성 API
     * @param requestDto 셍성하는 퀴즈카드 정보
     * @param user 퀴즈카드를 생성하는 유저 정보
     */
    CardResponseDto createCard(CardRequestDto requestDto, User user);
}
