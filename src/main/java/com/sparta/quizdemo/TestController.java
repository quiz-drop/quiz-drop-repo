package com.sparta.quizdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/login-test")
    String loginTest(){
        return "login-test";
    }


    @GetMapping("/mail-test")
    String mailTest(){
        return "mail-test";
    }
}
