package com.websecurity.app01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InjectionWebController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/demo")
    public String demo() {
        return "search-demo";
    }

    @GetMapping("/demo/search-raw")
    public String searchRaw(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("query", query);
        return "search-demo-raw";
    }

    @GetMapping("/demo/search-safe")
    public String searchSafe(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("query", query);
        return "search-demo-safe";
    }

    @GetMapping("/comment-raw")
    public String commentRaw(Model model) {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("comments", comments);
        return "comment-demo-raw";
    }

    @GetMapping("/comment-safe")
    public String commentSafe(Model model) {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("comments", comments);
        return "comment-demo-safe";
    }
}