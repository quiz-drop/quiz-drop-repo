package com.sparta.quizdemo.option.entity;

import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.option.dto.OptionRequestDto;
import com.sparta.quizdemo.order.entity.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @Column(name = "option_price", nullable = false)
    private Long optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartItem_id")
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderItem_id")
    private OrderItem orderItem;

    public Option(OptionRequestDto optionRequestDto) {
        this.category = optionRequestDto.getCategory();
        this.optionName = optionRequestDto.getOptionName();
        this.optionPrice = optionRequestDto.getOptionPrice();
    }

    public void update(OptionRequestDto optionRequestDto) {
        this.category = optionRequestDto.getCategory();
        this.optionName = optionRequestDto.getOptionName();
        this.optionPrice = optionRequestDto.getOptionPrice();
    }
}
