package com.sparta.quizdemo.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String productName;

    @NotBlank(message = "필수 입력 값입니다.")
    private Long productPrice;

    private String productImage;

    @NotBlank(message = "필수 입력 값입니다.")
    private String productIntro;

    @NotBlank(message = "필수 입력 값입니다.")
    private Long cookingTime;

    private Long orderCount = 0L;

    @NotBlank(message = "필수 입력 값입니다.")
    private String category;
}
