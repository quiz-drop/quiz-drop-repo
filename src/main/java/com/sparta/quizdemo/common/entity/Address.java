package com.sparta.quizdemo.common.entity;

import com.sparta.quizdemo.user.SignupRequestDto;
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

    public Address(SignupRequestDto requestDto, User user){
        this.zip_code = requestDto.getZip_code();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
        this.user = user;
    }
}
