package com.sparta.quizdemo.answer.controller;

import com.sparta.quizdemo.answer.dto.AnswerRequestDto;
import com.sparta.quizdemo.answer.service.AnswerServiceImpl;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnswerController {

    private final AnswerServiceImpl answerService;

    @PostMapping("/card/{cardNo}/answer")
    public ResponseEntity<ApiResponseDto> postAnswer(@PathVariable Long cardNo, @RequestBody AnswerRequestDto answerRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return answerService.postAnswer(cardNo, answerRequestDto, userDetails.getUser());
    }
}
