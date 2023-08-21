package com.sparta.quizdemo.order.entity;

import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.product.entity.Option;
import com.sparta.quizdemo.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.REMOVE)
    private List<Option> optionList = new ArrayList<>();

    public OrderItem(CartItem cartItem, Order order) {
        this.quantity = cartItem.getQuantity();
        this.order = order;
        this.product = cartItem.getProduct();
        this.optionList = cartItem.getOptionList();
    }
}
