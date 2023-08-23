package com.sparta.quizdemo.option.dto;

import lombok.Getter;

@Getter
public class OptionRequestDto {
    private String category;
    private String optionName;
    private Long optionPrice;
}
