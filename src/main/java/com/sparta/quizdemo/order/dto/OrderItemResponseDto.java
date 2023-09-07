package com.sparta.quizdemo.order.dto;

import com.sparta.quizdemo.option.dto.OptionResponseDto;
import com.sparta.quizdemo.order.entity.OrderItem;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderItemResponseDto {
    private String productName;
    private Integer quantity;
    private List<OptionResponseDto> optionList;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.productName = orderItem.getProduct().getProductName();
        this.quantity = orderItem.getQuantity();
        this.optionList = orderItem.getOptionList().stream().map(OptionResponseDto::new).collect(Collectors.toList());
    }
}
