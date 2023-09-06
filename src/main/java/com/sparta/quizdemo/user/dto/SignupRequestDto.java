package com.sparta.quizdemo.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    /*
    * 영문자, 숫자 4글자 이상
    * */
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{4,}$")
    private String username;
    /*
    * 영문자, 숫자, 특수문자가 포함된 4글자 이상
    * */
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{4,}$")
    private String password;


    /*
    * 특수문자 금지
    * */

    private String nickname;

    /*
     * 우편번호
     * */
    private String zip_code;

    /*
     * 주소 1 ~~동 까지
     * */
    private String address1;

    /*
     * 주소 2
     * */
    private String address2;


    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "유효하지 않은 이메일 주소입니다.")
    @NotBlank
    private String email;
    private boolean admin = false;
    private String adminToken = "";

    @JsonIgnore
    private Long orderCount = 0L;
}