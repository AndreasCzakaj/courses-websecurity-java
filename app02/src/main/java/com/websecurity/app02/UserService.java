package com.websecurity.app02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HashService {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use bcrypt with appropriate cost factor (12-15 recommended)
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public void createUser(String email, String password) {
        validatePasswordStrength(password);

        User user = new User();
        user.setEmail(email);

        // Secure password hashing with automatic salt
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        //user.setCreatedAt(Instant.now());
        //user.setPasswordChangedAt(Instant.now());

        userRepository.save(user);
    }

    void validatePasswordStrength(String password) {
        // impl me
    }
    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.isAccountNonLocked()) {
            // Secure password verification with timing attack protection
            boolean matches = passwordEncoder.matches(password, user.getPassword());

            if (matches) {
                resetFailedLoginAttempts(user);
                updateLastLoginTime(user);
                return true;
            } else {
                handleFailedLogin(user);
            }
        }

        // Prevent timing attacks - always perform password check
        passwordEncoder.matches("dummy", "$2a$12$dummy.hash.to.prevent.timing");
        return false;
    }
}
