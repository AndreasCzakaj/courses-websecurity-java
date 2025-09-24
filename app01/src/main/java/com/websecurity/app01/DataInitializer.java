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
            userRepository.save(new User("admin", "admin@example.com", "admin123", "Admin", "User", "ADMIN", generateSecureUuid()));
            userRepository.save(new User("john_doe", "john@example.com", "password123", "John", "Doe", "USER", generateSecureUuid()));
            userRepository.save(new User("jane_smith", "jane@example.com", "securepass", "Jane", "Smith", "USER", generateSecureUuid()));
            userRepository.save(new User("bob_wilson", "bob@example.com", "mypassword", "Bob", "Wilson", "MANAGER", generateSecureUuid()));

            System.out.println("Sample users initialized in database");
        }

        // Initialize some sample comments for demo purposes
        if (commentRepository.count() == 0) {
            commentRepository.save(new Comment("Welcome to our comment system!", "Admin"));
            commentRepository.save(new Comment("This is a normal comment with no HTML.", "John"));
            commentRepository.save(new Comment("Try entering <script>alert('You're persistently pwn3d')</script> to see the difference between safe and unsafe rendering!", "Security Tester"));
            commentRepository.save(new Comment("<b>Bold text</b> and <i>italic text</i> - see how it renders differently!", "HTML User"));
            commentRepository.save(new Comment("<img src=\"nonexistent.jpg\" onerror=\"alert('Stored XSS!')\"> - This should be escaped in the safe version!", "Attacker"));

            System.out.println("Sample comments initialized in database");
        }
    }

    private String generateSecureUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}