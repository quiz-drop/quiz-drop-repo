package com.sparta.quizdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {


    @GetMapping("/chatRoom")
    public String getNextPage() {
        return "chatRoom";
    }

    @GetMapping("/chatting")
    public String getChatting() {
        return "chatting";
    }


}
