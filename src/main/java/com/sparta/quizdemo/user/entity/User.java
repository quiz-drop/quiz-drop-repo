package com.sparta.quizdemo.user.entity;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.order.entity.Order;
import com.sparta.quizdemo.sse.entity.Notification;
import com.sparta.quizdemo.user.dto.SignupRequestDto;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private Long orderCount;

    @Column(nullable = true)
    private String socialId;

    @Column(nullable = true)
    private String social;

//
//    @Column(nullable = false, unique = true)
//    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> order;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();

    //회원가입 생성자
    public User(SignupRequestDto requestDto,String password, UserRoleEnum role) {
        this.username = requestDto.getUsername();
        this.password = password;
        this.nickname = requestDto.getNickname();
        //this.email = requestDto.getEmail();
        this.role = role;
        this.orderCount = requestDto.getOrderCount();
    }

    //소셜 회원가입 생성자
    public User(String username, String password, String nickname, UserRoleEnum role, String socialId, String social) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.socialId = socialId;
        this.social = social;
    }

    public void update(UserRequestDto requestDto, String newPassword) {
        this.nickname = requestDto.getNickname();
        this.password = newPassword;
    }

    public User socialUpdate(String socialId, String social) {
        this.socialId = socialId;
        this.social = social;
        return this;
    }

}
