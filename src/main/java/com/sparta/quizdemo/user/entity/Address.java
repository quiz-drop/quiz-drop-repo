package com.sparta.quizdemo.user.entity;

import com.sparta.quizdemo.user.dto.SignupRequestDto;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String zip_code;

    @Column(nullable = false)
    private String address1;

    @Column(nullable = false)
    private String address2;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    //회원가입용
    public Address(SignupRequestDto requestDto, User user){
        this.zip_code = requestDto.getZip_code();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
        this.user = user;
    }

    //소셜 로그인 시 주소 등록용
    public Address(UserRequestDto requestDto, User user){
        this.zip_code = requestDto.getZip_code();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
        this.user = user;
    }

    public void update(UserRequestDto requestDto) {
        this.zip_code = requestDto.getZip_code();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
    }
}
