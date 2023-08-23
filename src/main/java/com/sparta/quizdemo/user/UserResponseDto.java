package com.sparta.quizdemo.user;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.common.entity.User;
import com.sparta.quizdemo.order.dto.OrderItemResponseDto;
import com.sparta.quizdemo.order.dto.OrderResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {
    private String username;
    private String nickname;
    private UserRoleEnum role;
    private String zip_code;
    private String address1;
    private String address2;
    private Cart cart;
    private List<OrderResponseDto> orderList;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.zip_code = user.getAddress().getZip_code();
        this.address1 = user.getAddress().getAddress1();
        this.address2 = user.getAddress().getAddress2();
        this.cart = user.getCart();
        this.orderList = user.getOrder().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }
}
