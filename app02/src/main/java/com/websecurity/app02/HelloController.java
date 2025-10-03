package com.websecurity.app02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> hello2() {
        return hello();
    }

    @GetMapping("/")
    public Map<String, String> hello() {
        return Map.of("hello", "world");
    }

}