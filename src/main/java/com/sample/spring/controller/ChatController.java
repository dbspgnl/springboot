package com.sample.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/list")
    public String list(Model model) {
        // List<Board> boards = boardRepository.findAll();
        // model.addAttribute("boards", boards);
        return "contents/chat/list";
    }
    
}
