package com.sparta.quizdemo.mail.controller;

import com.sparta.quizdemo.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public String MailSend(String mail){

        int number = mailService.sendMail(mail);

        String num = String.valueOf(number);

        return num;
    }

}