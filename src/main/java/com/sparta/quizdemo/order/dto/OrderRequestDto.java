package com.sparta.quizdemo.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Boolean delivery;
    private String request;
    private Long payment;

    @JsonIgnore
    private Boolean orderComplete = false;
}
