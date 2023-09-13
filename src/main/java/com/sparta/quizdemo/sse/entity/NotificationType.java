package com.sparta.quizdemo.sse.entity;

import lombok.Getter;

@Getter
public enum NotificationType {
    ORDER_CANCELLED("주문이 취소되었습니다."),
    ORDER_COMPLETED("주문이 완료되었습니다."),
    DELIVERY("배송이 시작되었습니다."),
    USER_UPDATE("정보 수정이 완료되었습니다.");

    private String message;

    NotificationType(String message) {
        this.message = message;
    }
}