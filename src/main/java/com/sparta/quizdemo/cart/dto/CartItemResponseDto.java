package com.sparta.quizdemo.cart.dto;

import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.option.dto.OptionResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartItemResponseDto {
    private Long id;
    private String productName;
    private Long productPrice;
    private String productImage;
    private Long cookingTime;
    private Integer quantity;
    private List<OptionResponseDto> optionList;

    public CartItemResponseDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productName = cartItem.getProduct().getProductName();
        this.productPrice = cartItem.getProduct().getProductPrice();
        this.productImage = cartItem.getProduct().getProductImage();
        this.cookingTime = cartItem.getProduct().getCookingTime();
        this.quantity = cartItem.getQuantity();
        this.optionList = cartItem.getOptionList().stream().map(OptionResponseDto::new).collect(Collectors.toList());
    }
}
