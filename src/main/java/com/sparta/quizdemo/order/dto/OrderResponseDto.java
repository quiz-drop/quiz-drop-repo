package com.sparta.quizdemo.order.dto;

import com.sparta.quizdemo.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {
    private Long orderid;
    private String username;
    private Long totalPrice;
    private String request;
    private LocalDateTime completeTime;
    private List<OrderItemResponseDto> orderItemList;
    private Boolean orderComplete;

    public OrderResponseDto(Order order) {
        this.orderid = order.getId();
        this.username = order.getUser().getUsername();
        this.totalPrice = order.getTotalPrice();
        this.request = order.getRequest();
        this.completeTime = order.getCompleteTime();
        this.orderItemList = order.getOrderItemList().stream().map(OrderItemResponseDto::new).collect(Collectors.toList());
        this.orderComplete = order.getOrderComplete();
    }
}
