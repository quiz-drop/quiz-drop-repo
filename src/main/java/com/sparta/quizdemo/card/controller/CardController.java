package com.sparta.quizdemo.card.controller;

import com.sparta.quizdemo.card.dto.CardRequestDto;
import com.sparta.quizdemo.card.dto.CardResponseDto;
import com.sparta.quizdemo.card.service.CardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {
    private final CardServiceImpl cardService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/card")
    public CardResponseDto createCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return cardService.createCard(requestDto, userDetails.getUser());
    }
}
