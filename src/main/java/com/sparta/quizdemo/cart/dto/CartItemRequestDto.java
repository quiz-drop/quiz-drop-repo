package com.sparta.quizdemo.cart.dto;

import lombok.Getter;

@Getter
public class CartItemRequestDto {
    private String productName;
    private Integer quantity;
}
