package com.sparta.quizdemo.product.dto;

import lombok.Getter;

@Getter
public class OptionRequestDto {
    private String category;
    private String optionName;
    private Long optionPrice;
}
