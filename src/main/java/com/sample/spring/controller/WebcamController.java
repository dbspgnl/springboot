package com.sample.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebcamController {

    @GetMapping("/cam")
    public String index() {
        return "contents/cam/webcam";
    }
    
}
