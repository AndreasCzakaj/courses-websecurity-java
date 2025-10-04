package com.websecurity.app01;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class CrLfWebController {

    @GetMapping("/crlfinjection")
    public ResponseEntity<String> crlfinjection(@RequestParam String language) {
        System.out.println("language: " + language);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Language", language);

        return new ResponseEntity<>("", headers, HttpStatus.FOUND);
    }
    // Attack: language = http://localhost:8080/crlfinjection?language=de%0D%0ASet-Cookie:+admin=true%0D%0ALocation:/home
    // Attack: language = http://localhost:8080/crlfinjection?language=en%0D%0A[2025-10-06T17:46:23+02:00]+WARN+Your system+has+been+hacked.+Send+us+EUR10,000+or+we+will+encrypt+all+your+data+in+1+hour.



    @GetMapping("/crlfcookie")
    public ResponseEntity<String> crlfcookie(@RequestParam String language) {
        System.out.println("language: " + language);
        // DANGEROUS: User input in cookie without validation
        ResponseCookie cookie = ResponseCookie.from("language", language)
                .httpOnly(true)
                .secure(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Preferred language set successfully");
    }
}
