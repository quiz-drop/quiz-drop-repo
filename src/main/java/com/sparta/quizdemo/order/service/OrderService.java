package com.sparta.quizdemo.order.service;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.cart.repository.CartRepository;
import com.sparta.quizdemo.cart.service.CartServiceImpl;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.entity.User;
import com.sparta.quizdemo.order.dto.OrderRequestDto;
import com.sparta.quizdemo.order.dto.OrderResponseDto;
import com.sparta.quizdemo.order.entity.Order;
import com.sparta.quizdemo.order.entity.OrderItem;
import com.sparta.quizdemo.order.repository.OrderItemRepository;
import com.sparta.quizdemo.order.repository.OrderRepository;
import com.sparta.quizdemo.user.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartServiceImpl cartService;

    public ResponseEntity<ApiResponseDto> createOrder(OrderRequestDto orderRequestDto, User user) {
        if (cartRepository.findByUserId(user.getId()).isEmpty()) {
            cartService.createCart(user);
        }

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NullPointerException("장바구니가 없습니다."));
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

        long totalPrice = 0L;
        long totalCookingTime = 0L;

        if (cartItemList != null) {
            for (CartItem cartItem : cartItemList) {
                totalPrice += (cartItem.getProduct().getProductPrice() * cartItem.getQuantity());
                totalCookingTime += (cartItem.getProduct().getCookingTime() * cartItem.getQuantity());
            }
        }

        // 현재 시간
        LocalDateTime localDateTime = LocalDateTime.now();
        // 현재 시간에 총 조리시간 더하기
        LocalDateTime completeTime = localDateTime.plusMinutes(totalCookingTime);

        if (orderRequestDto.getDelivery()) {
            totalPrice += 2000L;
            completeTime = completeTime.plusMinutes(10);
        }

        if (orderRequestDto.getPayment().equals(totalPrice)) {
            // 현재 유저의 order 생성
            Order order = new Order(user, totalPrice, completeTime, orderRequestDto.getRequest());
            orderRepository.save(order);

            if (cartItemList != null) {
                for (CartItem cartItem : cartItemList) {
                    OrderItem orderItem = new OrderItem(cartItem, order);
                    orderItemRepository.save(orderItem);
                    cartItemRepository.delete(cartItem);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("결제가 완료 되었습니다.", HttpStatus.CREATED.value()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("금액을 올바르게 입력해 주세요.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    public ResponseEntity<List<OrderResponseDto>> getOrderList() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            throw new NullPointerException("주문이 없습니다.");
        } else {
            List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
            for (Order order : orderList) {
                OrderResponseDto orderResponseDto = new OrderResponseDto(order);
                orderResponseDtoList.add(orderResponseDto);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
        }
    }

    public ResponseEntity<List<OrderResponseDto>> getMyOrders(User user) {
        List<Order> orderList = orderRepository.findAllByUserId(user.getId());
        if (orderList.isEmpty()) {
            throw new NullPointerException("주문이 없습니다.");
        } else {
            List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
            for (Order order : orderList) {
                OrderResponseDto orderResponseDto = new OrderResponseDto(order);
                orderResponseDtoList.add(orderResponseDto);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
        }
    }

    public ResponseEntity<ApiResponseDto> cancelOrder(Long orderNo, User user) {
        Order order = orderRepository.findById(orderNo).orElseThrow(() -> new NullPointerException("존재하지 않는 주문 번호입니다."));
        if (user.getId().equals(order.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            orderRepository.delete(order);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("주문이 취소 되었습니다.", HttpStatus.OK.value()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiResponseDto("해당 주문에 대한 권한이 없습니다.", HttpStatus.BAD_GATEWAY.value()));
        }
    }

    public void completeOrder() {
        List<Order> orderList = orderRepository.findAll();
        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                LocalDateTime localDateTime = LocalDateTime.now();
                if (order.getCompleteTime().isBefore(localDateTime)) {
                    for (OrderItem orderItem : order.getOrderItemList()) {
                        Long tempOrderCount = orderItem.getProduct().getOrderCount();
                        tempOrderCount = tempOrderCount + orderItem.getQuantity();
                        orderItem.getProduct().setOrderCount(tempOrderCount);
                    }
                    orderRepository.delete(order);
                }
            }
        }
    }
}
