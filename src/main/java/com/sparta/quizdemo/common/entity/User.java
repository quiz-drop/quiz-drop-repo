package com.sparta.quizdemo.common.entity;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.order.entity.Order;
import com.sparta.quizdemo.user.SignupRequestDto;
import com.sparta.quizdemo.user.UserRequestDto;
import com.sparta.quizdemo.user.UserRoleEnum;
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

//    public void addPostList(Post post){
//        this.postList.add(post);
//        post.setUser(this);
//    }



    //회원가입 생성자
    public User(SignupRequestDto requestDto,String password, UserRoleEnum role) {
        this.username = requestDto.getUsername();
        this.password = password;
        this.nickname = requestDto.getNickname();
        //this.email = requestDto.getEmail();
        this.role = role;
        this.orderCount = requestDto.getOrderCount();
    }

    public void update(UserRequestDto requestDto, String newPassword) {
        this.nickname = requestDto.getNickname();
        this.password = newPassword;
    }
}
