package com.websecurity.app01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class InjectionController {

    // Template: <p>Results for: ${query}</p>
    // Attack: http://localhost:8080/injection/xss/reflective/search?query=<script>alert('Pwn3d!')</script>
    @GetMapping("/injection/xss/reflective/search")
    public String search(@RequestParam String query) {
        return template(String.format("<p>Results for: %s</p>", query));
    }

    String template(String content) {
        return String.format("<!DOCTYPE html><html><head><title>Demo</title></head><body>%s</body></html>", content);
    }
}