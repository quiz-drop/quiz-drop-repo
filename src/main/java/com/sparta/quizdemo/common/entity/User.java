package com.sparta.quizdemo.common.entity;

import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.user.SignupRequestDto;
import com.sparta.quizdemo.user.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Long  point;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Cart cart;

//
//    //user는 여러개의 comment를 가질 수 있음
//    @OneToMany(mappedBy = "user")
//    List<Comment> commentList = new ArrayList<>();


//    public void addPostList(Post post){
//        this.postList.add(post);
//        post.setUser(this);
//    }



    //회원가입 생성자
    public User(SignupRequestDto requestDto,String password, UserRoleEnum role) {
        this.username = requestDto.getUsername();
        this.password = password;
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
        this.point = 0L;
        this.role = role;
    }
}
