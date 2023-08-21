package com.sparta.quizdemo.product.entity;

import com.sparta.quizdemo.product.dto.OptionRequestDto;
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
