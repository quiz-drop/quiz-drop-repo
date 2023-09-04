package com.sparta.quizdemo.product.entity;

import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.product.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "products")
public class Product extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Long productPrice;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_intro", nullable = false)
    private String productIntro;

    @Column(name = "cooking_time", nullable = false)
    private Long cookingTime;

    @Setter
    @Column(name = "product_ordercount")
    private Long orderCount;

    @Column(name = "category", nullable = false)
    private String category;

    public Product(ProductRequestDto productRequestDto) {
        this.productName = productRequestDto.getProductName();
        this.productPrice = productRequestDto.getProductPrice();
        this.productImage = productRequestDto.getProductImage();
        this.productIntro = productRequestDto.getProductIntro();
        this.cookingTime = productRequestDto.getCookingTime();
        this.orderCount = productRequestDto.getOrderCount();
        this.category = productRequestDto.getCategory();
    }

    public void update(ProductRequestDto productRequestDto, Long tempCount) {
        this.productName = productRequestDto.getProductName();
        this.productPrice = productRequestDto.getProductPrice();
        this.productImage = productRequestDto.getProductImage();
        this.productIntro = productRequestDto.getProductIntro();
        this.cookingTime = productRequestDto.getCookingTime();
        this.orderCount = tempCount;
        this.category = productRequestDto.getCategory();
    }
}
