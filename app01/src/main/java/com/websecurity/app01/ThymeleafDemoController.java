package com.websecurity.app01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThymeleafDemoController {

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
}