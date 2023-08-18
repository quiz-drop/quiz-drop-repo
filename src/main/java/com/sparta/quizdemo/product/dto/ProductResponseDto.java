package com.sparta.quizdemo.product.dto;

import com.sparta.quizdemo.product.entity.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductResponseDto {
    private Long id;
    private String productName;
    private Long productPrice;
    private String productImage;
    private String productIntro;
    private String cookingTime;
    private Long orderCount;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productImage = product.getProductImage();
        this.productIntro = product.getProductIntro();
        this.cookingTime = product.getCookingTime();
        this.orderCount = product.getOrderCount();
        this.category = product.getCategory();
        this.createdAt = product.getCreatedAt();
        this.modifiedAt = product.getModifiedAt();
    }
}
