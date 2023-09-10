package com.sparta.quizdemo.user.entity;

import com.sparta.quizdemo.backoffice.dto.OneUserRequestDto;
import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.comment.entity.Comment;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Table(name = "users")
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //댓글 좋아요를 위해 추가
    @ColumnDefault("false")
    @Column(nullable = false, name = "user_bool")
    private boolean ubool;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orderList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    //회원가입 생성자
    public User(SignupRequestDto requestDto, String password, UserRoleEnum role) {
        this.username = requestDto.getUsername();
        this.password = password;
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.role = role;
        this.orderCount = requestDto.getOrderCount();
    }

    //소셜 회원가입 생성자
    public User(String username, String password, String nickname, UserRoleEnum role, String email, String socialId, String social) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.email = email;
        this.socialId = socialId;
        this.social = social;
    }

    //새로운 비밀번호까지 변경
    public void update(UserRequestDto requestDto, String newPassword) {
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.password = newPassword;
    }

    //소셜 유저인 경우
    public void update(UserRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
    }

    //유저 비밀번호변경
    public void update(String password) {
        this.password = password;
    }

    public User socialUpdate(String socialId, String social) {
        this.socialId = socialId;
        this.social = social;
        return this;
    }

    public void oneUserUpdate(OneUserRequestDto userRequestDto) {
        this.username = userRequestDto.getUsername();
        this.nickname = userRequestDto.getNickname();
    }

    public void updateUser(UserRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
    }
}
