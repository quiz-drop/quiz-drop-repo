package com.sparta.quizdemo.cart.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CartItemRequestDto {
    private Integer quantity;
    private List<Long> optionList;
}
