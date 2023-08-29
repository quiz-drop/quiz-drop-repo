package com.sparta.quizdemo.option.service;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.option.dto.OptionRequestDto;
import com.sparta.quizdemo.option.dto.OptionResponseDto;
import com.sparta.quizdemo.option.entity.Option;
import com.sparta.quizdemo.option.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;

    public ResponseEntity<OptionResponseDto> createOption(OptionRequestDto optionRequestDto) {
        if (optionRepository.findByOptionName(optionRequestDto.getOptionName()).isPresent()) {
            throw new IllegalArgumentException("중복된 옵션이름이 존재합니다.");
        } else {
            Option option = new Option(optionRequestDto);
            optionRepository.save(option);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OptionResponseDto(option));
        }
    }

    // 모든 옵션 목록 조회
    public ResponseEntity<List<OptionResponseDto>> getOptionList() {
        List<Option> optionList = optionRepository.findAllByOrderByCategory();
        List<OptionResponseDto> optionResponseDtoList = new ArrayList<>();

        for (Option option : optionList) {
            optionResponseDtoList.add(new OptionResponseDto(option));
        }

        return ResponseEntity.status(HttpStatus.OK).body(optionResponseDtoList);
    }

    // 카테고리 별 옵션 목록 조회
    public ResponseEntity<List<OptionResponseDto>> getOptions(String categoryName) {
        List<Option> optionList = optionRepository.findAllByCategory(categoryName);
        List<OptionResponseDto> optionResponseDtoList = new ArrayList<>();

        for (Option option : optionList) {
            optionResponseDtoList.add(new OptionResponseDto(option));
        }

        return ResponseEntity.status(HttpStatus.OK).body(optionResponseDtoList);
    }

    public ResponseEntity<OptionResponseDto> updateOption(Long optionNo, OptionRequestDto optionRequestDto) {
        Option option = optionRepository.findById(optionNo).orElseThrow(() -> new NullPointerException("해당 번호의 옵션이 존재하지 않습니다."));
        option.update(optionRequestDto);
        optionRepository.save(option);
        return ResponseEntity.status(HttpStatus.OK).body(new OptionResponseDto(option));
    }

    public ResponseEntity<ApiResponseDto> deleteOption(Long optionNo) {
        Option option = optionRepository.findById(optionNo).orElseThrow(() -> new NullPointerException("해당 번호의 옵션이 존재하지 않습니다."));
        optionRepository.delete(option);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("옵션이 삭제 되었습니다", HttpStatus.OK.value()));
    }
}
