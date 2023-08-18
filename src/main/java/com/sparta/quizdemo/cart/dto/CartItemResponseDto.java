package com.sparta.quizdemo.cart.dto;

import com.sparta.quizdemo.cart.entity.CartItem;
import lombok.Getter;

@Getter
public class CartItemResponseDto {
    private String productName;
    private Long productPrice;
    private String productImage;
    private Long cookingTime;
    private Integer quantity;

    public CartItemResponseDto(CartItem cartItem) {
        this.productName = cartItem.getProduct().getProductName();
        this.productPrice = cartItem.getProduct().getProductPrice();
        this.productImage = cartItem.getProduct().getProductImage();
        this.cookingTime = cartItem.getProduct().getCookingTime();
        this.quantity = cartItem.getQuantity();
    }
}
