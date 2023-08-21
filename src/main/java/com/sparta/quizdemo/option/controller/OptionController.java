package com.sparta.quizdemo.option.controller;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.option.dto.OptionRequestDto;
import com.sparta.quizdemo.option.dto.OptionResponseDto;
import com.sparta.quizdemo.option.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OptionController {
    private final OptionService optionService;

    // 옵션 생성
    @Transactional
    @Secured("ROLE_ADMIN")
    @PostMapping("/product/option")
    public ResponseEntity<OptionResponseDto> createOption(@RequestBody OptionRequestDto optionRequestDto) {
        return optionService.createOption(optionRequestDto);
    }

    // 카테고리별 옵션 목록 조회
    @GetMapping("/product/options/{categoryName}")
    public ResponseEntity<List<OptionResponseDto>> getOptions(@PathVariable String categoryName) {
        return optionService.getOptions(categoryName);
    }

    // 옵션 수정
    @Transactional
    @Secured("ROLE_ADMIN")
    @PutMapping("/product/option/{optionNo}")
    public ResponseEntity<OptionResponseDto> updateOption(@PathVariable Long optionNo, @RequestBody OptionRequestDto optionRequestDto) {
        return optionService.updateOption(optionNo, optionRequestDto);
    }

    // 옵션 삭제
    @Transactional
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/product/option/{optionNo}")
    public ResponseEntity<ApiResponseDto> deleteOption(@PathVariable Long optionNo) {
        return optionService.deleteOption(optionNo);
    }
}
