package com.sparta.quizdemo.card.controller;

import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.card.dto.CardResponseDto;
import com.sparta.quizdemo.card.service.CardServiceImpl;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {
    private final CardServiceImpl cardService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/card")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto requestDto) {
        return cardService.createCard(requestDto);
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardResponseDto>> getCards() {
        return cardService.getCards();
    }

    @GetMapping("/card/{cardNo}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long cardNo) {
        return cardService.getCard(cardNo);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/card/{cardNo}")
    public ResponseEntity<ApiResponseDto> updateCard(@PathVariable Long cardNo, @RequestBody CardRequestDto cardRequestDto) {
        return cardService.updateCard(cardNo, cardRequestDto);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/card/{cardNo}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long cardNo) {
        return cardService.deleteCard(cardNo);
    }
}
