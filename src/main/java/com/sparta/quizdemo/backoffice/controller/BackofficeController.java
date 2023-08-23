package com.sparta.quizdemo.backoffice.controller;

import com.sparta.quizdemo.backoffice.dto.KeywordRequestDto;
import com.sparta.quizdemo.backoffice.entity.Visitor;
import com.sparta.quizdemo.backoffice.service.BackofficeService;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class BackofficeController {
    private final BackofficeService backofficeService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/visitors")
    public ResponseEntity<List<Visitor>> getVisitors() {
        return backofficeService.getVisitors();
    }

    // ip 주소로 방문자 찾기
    @Secured("ROLE_ADMIN")
    @GetMapping("/visitor")
    public ResponseEntity<List<Visitor>> findVisitor(@RequestBody KeywordRequestDto keywordRequestDto) {
        return backofficeService.findVisitor(keywordRequestDto.getKeyword());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/countvisit")
    public Integer countVisitor() {
        return backofficeService.countVisitor();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/countorder")
    public Integer countOrder() { return backofficeService.countOrder(); }

    @Secured("ROLE_ADMIN")
    @GetMapping("/userlist")
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return backofficeService.getUserList();
    }

    // username 과 nickname 으로 유저 찾기
    @Secured("ROLE_ADMIN")
    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDto>> findOneUser(@RequestBody KeywordRequestDto keywordRequestDto) {
        return backofficeService.findOneUser(keywordRequestDto.getKeyword());
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user/{userNo}")
    public ResponseEntity<ApiResponseDto> deleteOneUser(@PathVariable Long userNo) {
        return backofficeService.deleteOneUser(userNo);
    }
}
