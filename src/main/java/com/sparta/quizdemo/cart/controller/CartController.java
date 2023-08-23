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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @Transactional
    @PostMapping("/cart/{productNo}")
    public ResponseEntity<ApiResponseDto> takeItem(@PathVariable Long productNo, @RequestBody CartItemRequestDto cartItemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.takeItem(productNo, cartItemRequestDto, userDetails.getUser());
    }

    // 카트 상품 개수 수정
    @Transactional
    @PutMapping("/cartitem/{cartItemNo}")
    public ResponseEntity<CartResponseDto> updateCartItem(@PathVariable Long cartItemNo, @RequestBody CartItemRequestDto cartItemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.updateCartItem(cartItemNo, cartItemRequestDto, userDetails.getUser());
    }

    // 카트에서 상품 빼기
    @Transactional
    @DeleteMapping("/cart/{cartItemNo}")
    public ResponseEntity<ApiResponseDto> deleteItem(@PathVariable Long cartItemNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.deleteItem(cartItemNo, userDetails.getUser());
    }
}
