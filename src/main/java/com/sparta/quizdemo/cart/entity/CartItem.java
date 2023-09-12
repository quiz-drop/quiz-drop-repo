package com.sparta.quizdemo.cart.entity;

import com.sparta.quizdemo.option.entity.Option;
import com.sparta.quizdemo.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    private List<Option> optionList = new ArrayList<>();

    public CartItem(Integer quantity, Cart cart, Product product, List<Option> options) {
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
        this.optionList = options;
    }
}
