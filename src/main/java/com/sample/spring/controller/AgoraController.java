package com.sample.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgoraController {

    @GetMapping("/agora")
    public String index() {
        return "contents/agora/webcam";
    }

    @GetMapping("/test")
    public String test() {
        return "contents/test/index";
    }
    
}
