package com.websecurity.app01;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/csrf")
public class CsrfController {

    // Attack: http://localhost:8080/csrf/addRoute?route=evil-ip
    @GetMapping("/addRoute")
    public Map<String, Object> addRouteGet(@RequestParam String route) {
        return addRoute(route);
    }

    @PostMapping("/addRoute")
    public Map<String, Object> addRouteGetPost(@RequestParam String route) {
        return addRoute(route);
    }

    Map<String, Object> addRoute(String route) {
        System.out.println("addRoute: " + route);
        return Map.of("result", "success", "addedRoute", route);
    }
}