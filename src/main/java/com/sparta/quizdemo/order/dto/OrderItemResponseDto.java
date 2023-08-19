package com.sparta.quizdemo.order.dto;

import com.sparta.quizdemo.order.entity.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {
    private String productName;
    private Long productPrice;
    private String productImage;
    private Integer quantity;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.productName = orderItem.getProduct().getProductName();
        this.productPrice = orderItem.getProduct().getProductPrice();
        this.productImage = orderItem.getProduct().getProductImage();
        this.quantity = orderItem.getQuantity();
    }
}
