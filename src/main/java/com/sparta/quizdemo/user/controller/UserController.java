package com.sparta.quizdemo.user.controller;

import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.user.dto.SignupRequestDto;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import com.sparta.quizdemo.user.dto.UserResponseDto;
import com.sparta.quizdemo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @Operation(summary = "회원가입")
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        userService.signup(requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("회원가입 완료", HttpStatus.CREATED.value()));
    }

    //정보 수정
    @Operation(summary = "유저 정보 수정")
    @PutMapping("/user/info")
    public ResponseEntity<ApiResponseDto> updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserRequestDto requestDto) {
        userService.updateUser(requestDto,userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("정보 수정 완료", HttpStatus.OK.value()));
    }

    // 회원 탈퇴
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/user/info")
    public void deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserRequestDto requestDto) {
        userService.deleteUser(requestDto,userDetails.getUser());
    }

    // 회원 조회
    @Operation(summary = "회원 조회")
    @GetMapping("/user/info")
    public UserResponseDto getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUser(userDetails.getUser());
    }

    // 주소 조회
    @Operation(summary = "주소 여부 판별")
    @GetMapping("/user/info/address")
    public boolean getAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getAddress(userDetails.getUser());
    }

    // 로그 아웃
    @PostMapping("/user/logout")
    public ResponseEntity<ApiResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails.getUser()) ;
        return ResponseEntity.ok().body(new ApiResponseDto("로그 아웃 완료", HttpStatus.OK.value()));
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> handleMethodArgumentNotValidException(IllegalArgumentException ex) {
        ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ApiResponseDto> handleMethodArgumentNotValidException(NullPointerException ex) {
        ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

}
