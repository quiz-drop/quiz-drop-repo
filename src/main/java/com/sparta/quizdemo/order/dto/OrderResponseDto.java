package com.sparta.quizdemo.order.dto;

import com.sparta.quizdemo.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {
    private String username;
    private Long orderid;
    private Long totalPrice;
    private LocalDateTime completeTime;
    private List<OrderItemResponseDto> orderItemList;

    public OrderResponseDto(Order order) {
        this.username = order.getUser().getUsername();
        this.orderid = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.completeTime = order.getCompleteTime();
        this.orderItemList = order.getOrderItemList().stream().map(OrderItemResponseDto::new).collect(Collectors.toList());
    }
}
