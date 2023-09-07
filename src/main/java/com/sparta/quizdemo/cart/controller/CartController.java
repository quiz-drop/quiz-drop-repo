package com.sparta.quizdemo.cart.controller;

import com.sparta.quizdemo.cart.dto.CartItemRequestDto;
import com.sparta.quizdemo.cart.dto.CartResponseDto;
import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.cart.service.CartServiceImpl;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "카트 생성", description = "미리 카트를 생성하지 않아도 다른 메서드를 실행할 때 자동으로 카트를 생성합니다.")
    @PostMapping("/cart")
    public Cart createCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.createCart(userDetails.getUser());
    }

    @Operation(summary = "카트 내부 조회")
    @GetMapping("/cart")
    public ResponseEntity<CartResponseDto> getCartItems(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.getCartItems(userDetails.getUser());
    }

    @Operation(summary = "카트에 상품 담기")
    @Transactional
    @PostMapping("/cart/{productNo}")
    public ResponseEntity<ApiResponseDto> takeItem(@PathVariable Long productNo, @RequestBody CartItemRequestDto cartItemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.takeItem(productNo, cartItemRequestDto, userDetails.getUser());
    }

    @Operation(summary = "카트 내부 상품 개수 수정")
    @Transactional
    @PutMapping("/cartitem/{cartItemNo}")
    public ResponseEntity<CartResponseDto> updateCartItem(@PathVariable Long cartItemNo, @RequestBody CartItemRequestDto cartItemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.updateCartItem(cartItemNo, cartItemRequestDto, userDetails.getUser());
    }

    @Operation(summary = "카트에서 상품 취소")
    @Transactional
    @DeleteMapping("/cart/{cartItemNo}")
    public ResponseEntity<ApiResponseDto> deleteItem(@PathVariable Long cartItemNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.deleteItem(cartItemNo, userDetails.getUser());
    }
}
