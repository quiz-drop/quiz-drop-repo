package com.sparta.quizdemo.cart.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CartItemRequestDto {
    private Integer quantity;
    private List<Long> optionList;
}
