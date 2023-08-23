package com.sparta.quizdemo.order.controller;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.order.dto.OrderRequestDto;
import com.sparta.quizdemo.order.dto.OrderResponseDto;
import com.sparta.quizdemo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    // 카트 아이템 결제 및 주문 생성
    @PostMapping("/order")
    public ResponseEntity<ApiResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(orderRequestDto, userDetails.getUser());
    }

    // 모든 주문 목록 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderList() {
        return orderService.getOrderList();
    }

    // 나의 주문 목록 조회
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getMyOrders(userDetails.getUser());
    }

    // 주문 취소
    @DeleteMapping("/order/{orderNo}")
    public ResponseEntity<ApiResponseDto> cancelOrder(@PathVariable Long orderNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.cancelOrder(orderNo, userDetails.getUser());
    }

    // 만료 시간이 된 주문 삭제
    @Scheduled(cron = "0 */5 * * * *") // 5분마다 업데이트
    public void completeOrder() {
        orderService.completeOrder();
    }
}
