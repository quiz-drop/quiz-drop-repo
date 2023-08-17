package com.sparta.quizdemo.card.service;

import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.card.dto.CardResponseDto;
import com.sparta.quizdemo.card.entity.Card;
import com.sparta.quizdemo.card.repository.CardRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final CardRepository cardRepository;

    @Override
    public ResponseEntity<CardResponseDto> createCard(CardRequestDto requestDto) {
        Card card = new Card(requestDto);
        cardRepository.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CardResponseDto(card));
    }

    @Override
    public ResponseEntity<List<CardResponseDto>> getCards() {
        List<Card> cards = cardRepository.findAllByOrderByCreatedAtDesc();
        List<CardResponseDto> cardResponseDto = new ArrayList<>();

        for (Card card : cards) {
            cardResponseDto.add(new CardResponseDto(card));
        }

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDto);
    }

    @Override
    public ResponseEntity<CardResponseDto> getCard(Long cardNo) {
        Card card = cardRepository.findById(cardNo).orElseThrow(() -> new NullPointerException("선택하신 퀴즈카드는 존재하지 않습니다."));
        return ResponseEntity.status(HttpStatus.OK).body(new CardResponseDto(card));
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateCard(Long cardNo, CardRequestDto cardRequestDto) {
        Card card = cardRepository.findById(cardNo).orElseThrow(() -> new NullPointerException("선택하신 퀴즈카드는 존재하지 않습니다."));
        card.update(cardRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("퀴즈카드 수정 완료", 200));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteCard(Long cardNo) {
        Card card = cardRepository.findById(cardNo).orElseThrow(() -> new NullPointerException("선택하신 퀴즈카드는 존재하지 않습니다."));
        cardRepository.delete(card);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("퀴즈카드 삭제 완료", 200));
    }
}
