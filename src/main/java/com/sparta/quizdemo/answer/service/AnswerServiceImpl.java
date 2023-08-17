package com.sparta.quizdemo.answer.service;

import com.sparta.quizdemo.answer.dto.AnswerRequestDto;
import com.sparta.quizdemo.answer.entity.Answer;
import com.sparta.quizdemo.answer.repository.AnswerRepository;
import com.sparta.quizdemo.card.entity.Card;
import com.sparta.quizdemo.card.repository.CardRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final CardRepository cardRepository;
    private final AnswerRepository answerRepository;
    private final MessageSource messageSource;

    @Override
    public ResponseEntity<ApiResponseDto> postAnswer(Long cardNo, AnswerRequestDto answerRequestDto, User user) {
        Card card = cardRepository.findById(cardNo).orElseThrow(
                () -> new NullPointerException(messageSource.getMessage(
                        "not.exist.post",
                        null,
                        "해당 카드가 존재하지 않습니다",
                        Locale.getDefault()
                ))
        );
        Answer answer = answerRepository.save(new Answer(answerRequestDto, user, card));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("정답 등록 완료", 201));
    }
}
