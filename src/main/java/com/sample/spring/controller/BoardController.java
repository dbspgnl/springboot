package com.sample.spring.controller;

// import com.sample.spring.model.Board;
// import com.sample.spring.repository.BoardRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    // @Autowired
    // private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model) {
        // List<Board> boards = boardRepository.findAll();
        // model.addAttribute("boards", boards);
        return "board/list";
    }

}
