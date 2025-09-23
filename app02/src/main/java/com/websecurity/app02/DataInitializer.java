package com.websecurity.app02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

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
    }

    private String generateSecureUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}