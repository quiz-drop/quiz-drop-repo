package com.sparta.quizdemo.order.controller;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.order.dto.OrderRequestDto;
import com.sparta.quizdemo.order.dto.OrderResponseDto;
import com.sparta.quizdemo.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "카트 아이템 결제 및 주문 생성")
    @Transactional
    @PostMapping("/order")
    public ResponseEntity<ApiResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(orderRequestDto, userDetails.getUser());
    }

    @Operation(summary = "전체 주문 목록 조회", description = "관리자 제한")
    @Secured("ROLE_ADMIN")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderList() {
        return orderService.getOrderList();
    }

    @Operation(summary = "나의 주문 목록 조회")
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getMyOrders(userDetails.getUser());
    }

    @Operation(summary = "주문 취소")
    @DeleteMapping("/order/{orderNo}")
    public ResponseEntity<ApiResponseDto> cancelOrder(@PathVariable Long orderNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.cancelOrder(orderNo, userDetails.getUser());
    }

    // 완료된 주문 삭제
    @Scheduled(cron = "0 */5 * * * *") // 5분마다 업데이트
    public void completeOrder() {
        orderService.completeOrder();
    }
}
