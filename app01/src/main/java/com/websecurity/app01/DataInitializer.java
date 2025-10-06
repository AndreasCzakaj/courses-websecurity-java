package com.websecurity.app01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize some sample users for demo purposes
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "admin@example.com", "admin123", "Admin", "User", "ADMIN", "e99f54b7b2524180be98e274c68bb38c"));
            userRepository.save(new User("john_doe", "john@example.com", "password123", "John", "Doe", "USER", "f8f3c498d005480c8deaa011ea405ac8"));
            userRepository.save(new User("jane_smith", "jane@example.com", "securepass", "Jane", "Smith", "USER", "54c98d2ade2945a1af844bf8f3e2b721"));
            userRepository.save(new User("bob_wilson", "bob@example.com", "mypassword", "Bob", "Wilson", "MANAGER", "daa55f37d95f443da206315148c26599"));

            System.out.println("Sample users initialized in database");
        }

        // Initialize some sample comments for demo purposes
        if (commentRepository.count() == 0) {
            commentRepository.save(new Comment("Welcome to our comment system!", "Admin"));
            commentRepository.save(new Comment("This is a normal comment with no HTML.", "John"));
            commentRepository.save(new Comment("Try entering <script>alert('You are persistently pwn3d')</script> to see the difference between safe and unsafe rendering!", "Security Tester"));
            commentRepository.save(new Comment("<b>Bold text</b> and <i>italic text</i> - see how it renders differently!", "HTML User"));
            commentRepository.save(new Comment("<img src=\"nonexistent.jpg\" onerror=\"alert('Stored XSS!')\"> - This should be escaped in the safe version!", "Attacker"));

            System.out.println("Sample comments initialized in database");
        }
    }

    private String generateSecureUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}