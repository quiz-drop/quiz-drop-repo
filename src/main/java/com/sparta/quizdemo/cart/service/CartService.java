package com.sparta.quizdemo.cart.service;

import com.sparta.quizdemo.cart.dto.CartItemRequestDto;
import com.sparta.quizdemo.cart.dto.CartResponseDto;
import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    /**
     * 카트 생성 API
     * @param user 카트 주인 정보
     */
    Cart createCart(User user);

    /**
     * 카트 내부 상품 목록 조회 API
     * @param user 카트 주인 정보
     */
    ResponseEntity<CartResponseDto> getCartItems(User user);

    /**
     * 카트에 상품 추가 API
     * @param cartItemRequestDto 추가하려는 상품 이름과 개수
     * @param user 카트 주인 정보
     */
    ResponseEntity<ApiResponseDto> getItem(CartItemRequestDto cartItemRequestDto, User user);

    /**
     * 카트에서 상품 제거 API
     * @param cartItemNo 제거하려는 장바구니 아이템 번호
     * @param user 카트 주인 정보
     */
    ResponseEntity<ApiResponseDto> deleteItem(Long cartItemNo, User user);

    /**
     * 카트 내부 상품 모두 제거 API
     * @param user
     */
    ResponseEntity<ApiResponseDto> clearCartItems(User user);
}
