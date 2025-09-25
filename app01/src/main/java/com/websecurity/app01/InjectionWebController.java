package com.websecurity.app01;

import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private NonceGenerator nonceGenerator;

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
    /*@CSPPolicy(
        scriptSrc = "'self'",           // Very strict - no nonces, no inline
        styleSrc = "'self' 'unsafe-inline'",  // Allow inline styles for demo
        strict = true
    )*/
    public String commentRaw(Model model) {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("comments", comments);
        return "comment-demo-raw";
    }

    @GetMapping("/comment-safe")
   /* @CSPPolicy(
        scriptSrc = "'self' 'nonce-{nonce}'",  // Allow nonce-based scripts
        styleSrc = "'self' 'nonce-{nonce}'"    // Allow nonce-based styles
    )*/
    public String commentSafe(Model model, HttpServletRequest request) {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("comments", comments);

        // Get nonce from CSP filter or generate fallback
        String nonce = (String) request.getAttribute("cspNonce");
        model.addAttribute("cspNonce", nonce);

        return "comment-demo-safe";
    }
}