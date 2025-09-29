package com.websecurity.app01;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/clickjacking")
public class ClickjackingWebController {

    @GetMapping("/showForm")
    public String show(
            HttpServletResponse response,
            @RequestParam (required = false) String to,
            @RequestParam(required = false) String amount,
            Model model) {
        //response.setHeader("X-Frame-Options", "DENY");
        model.addAttribute("to", to == null ? "" : to);
        model.addAttribute("amount", amount == null ? "" : amount);
        return "transfer-form";
    }

    @PostMapping("/doTransferMoney")
    public String transferMoney(
            HttpServletResponse response,
            @RequestParam String to,
            @RequestParam String amount,
            Model model) {
        //response.setHeader("X-Frame-Options", "DENY");
        String from = "me:1235487";
        Map<String, Object> out = Map.of("result", "success", "from", from, "to", to, "amount", amount);
        System.out.println("Transferring money: " + out.toString());
        model.addAttribute("from", from);
        model.addAttribute("to", to );
        model.addAttribute("amount", amount);
        model.addAttribute("result", "success");
        // this should be a redirect...
        return "transfer-result";
    }

    @PostMapping("/report-clickjacking-attempt")
    public String reportClickjackingAttempt(
            @RequestBody Map<String, String> body) {
        System.out.println("Clickjacking attempt detected: " + body);
        return "void";
    }

}