package com.sparta.quizdemo.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.quizdemo.auth.service.GoogleService;
import com.sparta.quizdemo.auth.service.KakaoService;
import com.sparta.quizdemo.auth.service.NaverService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final NaverService naverService;
    private final KakaoService kakaoService;
    private final GoogleService googleService;

    @GetMapping("/naver/login")
    public RedirectView naverLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        String token = naverService.naverLogin(code);

        // Set the JWT token as a cookie in the response
        Cookie jwtCookie = new Cookie("jwt", token.substring(7));
        jwtCookie.setMaxAge(3600);
        jwtCookie.setPath("/");

        // Add the cookie to the response
        response.addCookie(jwtCookie);
        return new RedirectView("/test");
    }

    @GetMapping("/kakao/login")
    public RedirectView kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        String token = kakaoService.kakaoLogin(code);

        // Set the JWT token as a cookie in the response
        Cookie jwtCookie = new Cookie("jwt", token.substring(7));
        jwtCookie.setMaxAge(3600);
        jwtCookie.setPath("/");

        // Add the cookie to the response
        response.addCookie(jwtCookie);
        return new RedirectView("/test");
    }

    @GetMapping("/google/login")
    public RedirectView googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        String token = googleService.googleLogin(code);

        // Set the JWT token as a cookie in the response
        Cookie jwtCookie = new Cookie("jwt", token.substring(7));
        jwtCookie.setMaxAge(3600);
        jwtCookie.setPath("/");

        // Add the cookie to the response
        response.addCookie(jwtCookie);
        return new RedirectView("/test");
    }
}
