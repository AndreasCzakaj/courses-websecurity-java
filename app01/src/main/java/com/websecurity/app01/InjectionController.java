package com.websecurity.app01;

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
    // Attack: http://localhost:8080/injection/xss/reflective/search?query=<script>alert('XSS')</script>
    // Java - Bad: Direct output without encoding
    @GetMapping("/injection/xss/reflective/search")
    public String search(@RequestParam String query) {
        return String.format("<!DOCTYPE html><html><head><title>Pwn3d</title></head><body><p>Results for: %s</p></body></html>", query);
    }

}