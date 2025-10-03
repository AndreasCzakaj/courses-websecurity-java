package com.websecurity.app02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        return "home";
    }

}