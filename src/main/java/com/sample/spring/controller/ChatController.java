package com.sample.spring.controller;

import java.util.List;

import com.sample.spring.model.Room;
import com.sample.spring.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/list")
    public String list(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "contents/chat/list";
    }
    
}
