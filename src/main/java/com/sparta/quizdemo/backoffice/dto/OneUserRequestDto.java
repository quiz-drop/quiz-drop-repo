package com.sparta.quizdemo.backoffice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OneUserRequestDto {
    @NotBlank(message = "유저명 입력이 안되었습니다.")
    private String username;

    @NotBlank(message = "닉네임 입력이 안되었습니다.")
    private String nickname;

    @NotBlank(message = "우편번호 입력이 안되었습니다.")
    private String zip_code;

    @NotBlank(message = "주소 입력이 안되었습니다.")
    private String address1;

    @NotBlank(message = "주소 입력이 안되었습니다.")
    private String address2;
}
