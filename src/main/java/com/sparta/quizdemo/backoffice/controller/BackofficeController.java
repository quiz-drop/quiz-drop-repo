package com.sparta.quizdemo.backoffice.controller;

import com.sparta.quizdemo.backoffice.entity.Visitor;
import com.sparta.quizdemo.backoffice.service.BackofficeService;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.user.dto.UserResponseDto;
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

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user/{userNo}")
    public ResponseEntity<ApiResponseDto> deleteOneUser(@PathVariable Long userNo) {
        return backofficeService.deleteOneUser(userNo);
    }
}
