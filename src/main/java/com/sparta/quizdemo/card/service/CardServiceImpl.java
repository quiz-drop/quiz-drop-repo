package com.sparta.quizdemo.card.service;

import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.card.dto.CardResponseDto;
import com.sparta.quizdemo.card.entity.Card;
import com.sparta.quizdemo.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final USerRepository uSerRepository;
    private final CardRepository cardRepository;

    @Override
    public CardResponseDto createCard(CardRequestDto requestDto, User user) {
        uSerRepository.findById(user.getId).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

        Card card = new Card(requestDto, user);
        cardRepository.save(card);
        return null;
    }
}
