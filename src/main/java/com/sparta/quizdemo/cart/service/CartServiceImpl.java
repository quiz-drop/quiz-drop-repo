package com.sparta.quizdemo.cart.service;

import com.sparta.quizdemo.cart.dto.CartItemRequestDto;
import com.sparta.quizdemo.cart.dto.CartResponseDto;
import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.cart.repository.CartRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.entity.User;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(User user) {
        if (cartRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalArgumentException("장바구니가 이미 존재합니다.");
        } else {
            Cart cart = new Cart(user);
            cartRepository.save(cart);
            return cart;
        }

//        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));
//        return cart;
    }

    @Override
    public ResponseEntity<CartResponseDto> getCartItems(User user) {
        Cart cart = cartRepository.findByUserId(user.getId()).orElse(createCart(user));
        return ResponseEntity.status(HttpStatus.OK).body(new CartResponseDto(user, cart));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getItem(CartItemRequestDto cartItemRequestDto, User user) {
        Product product = productRepository.findByProductName(cartItemRequestDto.getProductName()).orElseThrow(
                () -> new NullPointerException("해당 이름의 상품이 존재하지 않습니다."));
        Cart cart = cartRepository.findByUserId(user.getId()).orElse(createCart(user));

        if (cartItemRepository.findByProductId(product.getId()).isPresent()) {
            CartItem cartItem = cartItemRepository.findByProductId(product.getId()).orElseThrow();
            Integer tempQuantity = cartItem.getQuantity();
            cartItem.setQuantity(tempQuantity + 1);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem(cartItemRequestDto, cart, product);
            cartItemRepository.save(cartItem);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("상품을 장바구니에 담았습니다.", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteItem(Long cartItemNo, User user) {
        CartItem cartItem = cartItemRepository.findById(cartItemNo).orElseThrow(
                () -> new NullPointerException("해당 상품이 장바구니에 존재하지 않습니다."));
        cartItemRepository.delete(cartItem);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("상품을 장바구니에서 제거했습니다.", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> clearCartItems(User user) {
        List<CartItem> cartItemList = cartItemRepository.findAllByUserId(user.getId());

        for (CartItem cartItem : cartItemList) {
            cartItemRepository.delete(cartItem);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("유저의 장바구니를 비웠습니다.", HttpStatus.OK.value()));
    }
}
