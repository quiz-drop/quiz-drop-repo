package com.sparta.quizdemo.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    String test(){
        return "test";
    }

    @GetMapping("/login-test")
    String loginTest(){
        return "login-test";
    }
}
