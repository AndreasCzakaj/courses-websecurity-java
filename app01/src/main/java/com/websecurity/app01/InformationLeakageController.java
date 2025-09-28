package com.websecurity.app01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.security.SecureRandom;
import java.util.UUID;

// Cryptographically secure random UUID
// UUID secureUuid = UUID.randomUUID(); // Uses SecureRandom internally in modern JVMs


@RestController
public class InformationLeakageController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/information-leakage/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // http://localhost:8080/information-leakage/users/666
    // http://localhost:8080/murcs
    // http://localhost:8080/information-leakage/users/2
    @GetMapping("/information-leakage/users/{userId}")
    public Map<String, Object> informationLeakageDb(@PathVariable Long userId) {
        // Java - Bad: Exposing stack traces and database details
        try {
            Optional<User> user = userRepository.findUser(userId);
            //Optional<User> user = userRepository.findBySafeUserId(userId);

            if (user.isEmpty()) {
                // Simulating a database error that leaks information
                throw new RuntimeException(
                        String.format("SQL Error: User with ID '%s' doesn't exist in table user'", userId)
                );
            }

            return Map.of("status", "success", "user", user.get());
        } catch (Exception e) {
            // BAD: Exposing full stack trace and database structure
            return Map.of(
                "error", "Database error: " + e.getMessage(),
                "details", "Full stack trace: " + e.getClass().getName() + " at " + e.getStackTrace()[0].toString(),
                "database", "H2 in-memory database",
                "connection", "jdbc:h2:mem:testdb",
                "schema", "Available tables: users, hibernate_sequence"
            );
        }
    }

}