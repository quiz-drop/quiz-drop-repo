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
    }

    @Override
    public ResponseEntity<CartResponseDto> getCartItems(User user) {
//        Cart cart = cartRepository.findByUserId(user.getId()).orElse(createCart(user));

        if (cartRepository.findByUserId(user.getId()).isEmpty()) {
            createCart(user);
        }

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NullPointerException("장바구니가 없습니다."));
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getId());
        Long totalPrice = 0L;
        Long totalCookingTime = 0L;

        if (cartItemList != null) {
            for (CartItem cartItem : cartItemList) {
                totalPrice += (cartItem.getProduct().getProductPrice() * cartItem.getQuantity());
                totalCookingTime += (cartItem.getProduct().getCookingTime()) * cartItem.getQuantity();
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CartResponseDto(user, cart, totalPrice, totalCookingTime));
    }

    @Override
    public ResponseEntity<ApiResponseDto> saveCartItem(Long productNo, CartItemRequestDto cartItemRequestDto, User user) {
        Product product = productRepository.findById(productNo).orElseThrow(
                () -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));

        if (cartRepository.findByUserId(user.getId()).isEmpty()) {
            createCart(user);
        }
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NullPointerException("장바구니가 존재하지 않습니다."));

        if (cartItemRepository.findByProductIdAndCartId(product.getId(), cart.getId()).isPresent()) {
            CartItem cartItem = cartItemRepository.findByProductIdAndCartId(product.getId(), cart.getId()).orElseThrow();
            Integer tempQuantity = cartItem.getQuantity();
            cartItem.setQuantity(tempQuantity + cartItemRequestDto.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem(cartItemRequestDto.getQuantity(), cart, product);
            cartItemRepository.save(cartItem);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("상품을 장바구니에 담았습니다.", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<CartResponseDto> updateCartItem(Long cartItemNo, CartItemRequestDto cartItemRequestDto, User user) {
        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemNo, user.getCart().getId()).orElseThrow(
                () -> new NullPointerException("해당 상품이 장바구니에 존재하지 않습니다."));

        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return getCartItems(user);
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteItem(Long cartItemNo, User user) {
        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemNo, user.getCart().getId()).orElseThrow(
                () -> new NullPointerException("해당 상품이 장바구니에 존재하지 않습니다."));
        cartItemRepository.delete(cartItem);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("상품을 장바구니에서 제거했습니다.", HttpStatus.OK.value()));
    }
}
