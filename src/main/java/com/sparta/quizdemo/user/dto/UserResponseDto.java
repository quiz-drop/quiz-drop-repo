package com.sparta.quizdemo.user.dto;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.common.entity.User;
import com.sparta.quizdemo.order.dto.OrderResponseDto;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
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
    private Long orderCount;
    private List<OrderResponseDto> orderList;
    //댓글 내역 조회

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.zip_code = user.getAddress().getZip_code();
        this.address1 = user.getAddress().getAddress1();
        this.address2 = user.getAddress().getAddress2();
        this.orderCount = user.getOrderCount();
        this.orderList = user.getOrder().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }
}
