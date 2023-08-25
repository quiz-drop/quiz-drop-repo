package com.sparta.quizdemo.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    // 회원 정보 수정 시에

    @NotBlank(message = "닉네임 입력이 안되었습니다.")
    private String nickname;

    @NotBlank(message = "우편번호 입력이 안되었습니다.")
    private String zip_code;

    @NotBlank(message = "주소 입력이 안되었습니다.")
    private String address1;

    @NotBlank(message = "주소 입력이 안되었습니다.")
    private String address2;

    @NotBlank(message = "비밀번호 입력이 안되었습니다.")
    private String password;

    @NotBlank(message = "새 비밀번호 입력이 안되었습니다.")
    private String newPassword;


//    @Email(message = "올바른 이메일 형식이 아닙니다.")
//    private String email;

}
