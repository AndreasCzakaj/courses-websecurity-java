package com.websecurity.app02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;



@RestController
public class InjectionController {

    // Template: <p>Results for: ${query}</p>
    // Attack: http://localhost:8082/injection/xss/reflective/search?query=<script>alert('Pwn3d!')</script>
    @GetMapping("/injection/xss/reflective/search")
    public String search(@RequestParam String query) {
        return template(String.format("<p>Results for: %s</p>", query));
    }

    String template(String content) {
        return String.format("<!DOCTYPE html><html><head><title>Demo</title></head><body>%s</body></html>", content);
    }

}