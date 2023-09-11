package com.sparta.quizdemo.product.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sparta.quizdemo.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String productName;
    private Long productPrice;
    private String productImage;
    private String productIntro;
    private Long cookingTime;
    private Long orderCount;
    private String category;
    private Integer productScore;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
        this.productScore = product.getProductScore();
        this.createdAt = product.getCreatedAt();
        this.modifiedAt = product.getModifiedAt();
    }
}
