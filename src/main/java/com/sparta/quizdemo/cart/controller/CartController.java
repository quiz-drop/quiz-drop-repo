package com.sparta.quizdemo.cart.controller;

import com.sparta.quizdemo.cart.dto.CartItemRequestDto;
import com.sparta.quizdemo.cart.dto.CartResponseDto;
import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.cart.service.CartServiceImpl;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {
    private final CartServiceImpl cartService;

    // 카트 생성
    @PostMapping("/cart")
    public Cart createCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.createCart(userDetails.getUser());
    }

    // 카트 내부 조회
    @GetMapping("/cart")
    public ResponseEntity<CartResponseDto> getCartItems(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.getCartItems(userDetails.getUser());
    }

    // 카트에 상품 담기
    @PostMapping("/cartitem")
    public ResponseEntity<ApiResponseDto> getItem(@RequestBody CartItemRequestDto cartItemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.getItem(cartItemRequestDto, userDetails.getUser());
    }

    // 카트에서 상품 빼기
    @DeleteMapping("/cart/{cartItemNo}")
    public ResponseEntity<ApiResponseDto> deleteItem(@PathVariable Long cartItemNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.deleteItem(cartItemNo, userDetails.getUser());
    }

    // 카트 내부 상품 모두 제거
    @DeleteMapping("/cart")
    public ResponseEntity<ApiResponseDto> clearCartItems(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.clearCartItems(userDetails.getUser());
    }
}
