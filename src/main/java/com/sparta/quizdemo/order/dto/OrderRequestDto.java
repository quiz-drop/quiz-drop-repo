package com.sparta.quizdemo.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Boolean delivery;
    private String request;
    private Long payment;
}
