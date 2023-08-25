package com.sparta.quizdemo.backoffice.controller;

import com.sparta.quizdemo.backoffice.dto.KeywordRequestDto;
import com.sparta.quizdemo.backoffice.entity.Visitor;
import com.sparta.quizdemo.backoffice.service.BackofficeService;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.user.UserRequestDto;
import com.sparta.quizdemo.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "방문자 목록 조회 메서드", description = "관리자 제한. 전체 방문자 목록을 조회합니다.")
    @Secured("ROLE_ADMIN")
    @GetMapping("/visitors")
    public ResponseEntity<List<Visitor>> getVisitors() {
        return backofficeService.getVisitors();
    }

    @Operation(summary = "IP주소로 방문자 검색 메서드", description = "관리자 제한. IP주소에 키워드 넘버가 있는 방문자를 검색합니다.")
    @Secured("ROLE_ADMIN")
    @PutMapping("/visitor")
    public ResponseEntity<List<Visitor>> findVisitor(@RequestBody KeywordRequestDto keywordRequestDto) {
        return backofficeService.findVisitor(keywordRequestDto.getKeyword());
    }

    @Operation(summary = "방문자 수 카운터", description = "관리자 제한. 총 방문자 수 합산")
    @Secured("ROLE_ADMIN")
    @GetMapping("/countvisit")
    public Integer countVisitor() {
        return backofficeService.countVisitor();
    }

    @Operation(summary = "전체 주문 수 카운터", description = "관리자 제한. 총 주문 수 합산")
    @Secured("ROLE_ADMIN")
    @GetMapping("/countorder")
    public Long countOrder() { return backofficeService.countOrder(); }

    @Operation(summary = "유저 목록 조회", description = "관리자 제한. 전체 유저 목록을 조회합니다.")
    @Secured("ROLE_ADMIN")
    @GetMapping("/userlist")
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return backofficeService.getUserList();
    }

    @Operation(summary = "username 과 nickname 으로 유저 검색", description = "관리자 제한. username 과 nickname 에 키워드를 포함한 유저들을 검색합니다.")
    @Secured("ROLE_ADMIN")
    @PutMapping("/user")
    public ResponseEntity<List<UserResponseDto>> findOneUser(@RequestBody KeywordRequestDto keywordRequestDto) {
        return backofficeService.findOneUser(keywordRequestDto.getKeyword());
    }

    @Operation(summary = "일반 유저 정보 수정", description = "관리자 제한. 관리자가 아닌 유저의 정보를 수정합니다.")
    @Secured("ROLE_ADMIN")
    @PutMapping("/user/{userName}")
    public ResponseEntity<UserResponseDto> updateOneUSer(@PathVariable String userName, @RequestBody UserRequestDto userRequestDto) {
        return backofficeService.updateOneUSer(userName, userRequestDto);
    }

    @Operation(summary = "일반 유저 강제 탈퇴", description = "관리자 제한. 관리자가 아닌 유저의 정보를 삭제합니다.")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user/{userName}")
    public ResponseEntity<ApiResponseDto> deleteOneUser(@PathVariable String userName) {
        return backofficeService.deleteOneUser(userName);
    }
}
