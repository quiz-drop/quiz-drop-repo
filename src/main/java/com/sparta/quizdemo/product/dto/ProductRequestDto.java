package com.sparta.quizdemo.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String productName;

    @NotBlank(message = "필수 입력 값입니다.")
    private Long productPrice;

    @JsonIgnore
    private String productImage;

    @NotBlank(message = "필수 입력 값입니다.")
    private String productIntro;

    @NotBlank(message = "필수 입력 값입니다.")
    private Long cookingTime;

    @JsonIgnore
    private Long orderCount = 0L;

    @NotBlank(message = "필수 입력 값입니다.")
    private String category;

    @JsonIgnore
    private Integer productScore = 5;

    public void setFileName(String filename) {
        this.productImage = filename;
    }
}
