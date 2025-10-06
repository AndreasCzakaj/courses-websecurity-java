package com.websecurity.app01;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class InclusionWebController {

    static final Path templatesDir = Paths.get("./src/main/resources/templates");

    @GetMapping("/inclusion1")
    public ResponseEntity<String> inclusion1(@RequestParam String template) {
        System.out.println("inclusion1: " + template);
        try {
            // DANGEROUS: User input directly in file path
            Path templatePath = Paths.get(templatesDir.toAbsolutePath().toString(), template);
            System.out.println("templatePath: " + templatePath.toAbsolutePath().toString());
            String content = Files.readString(templatePath);
            System.out.println("content: " + content);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Template not found", HttpStatus.NOT_FOUND);
        }
    }
}
