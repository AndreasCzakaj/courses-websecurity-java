package com.websecurity.app02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
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
        user.setPasswordHash(hashedPassword);
        //user.setCreatedAt(Instant.now());
        //user.setPasswordChangedAt(Instant.now());

        userRepository.save(user);
    }

    void validatePasswordStrength(String password) {
        // impl me
    }
    public boolean authenticate(String email, String password) {
        Optional<User> maybeUser = userRepository.findByEmail(email);

        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            if (user.isAccountNonLocked()) {
                // Secure password verification with timing attack protection
                boolean matches = passwordEncoder.matches(password, user.getPasswordHash());

                // impl me
                /*if (matches) {
                    resetFailedLoginAttempts(user);
                    updateLastLoginTime(user);
                    return true;
                } else {
                    handleFailedLogin(user);
                }*/
            }
        }

        // Prevent timing attacks - always perform password check
        passwordEncoder.matches("dummy", "$2a$12$dummy.hash.to.prevent.timing");
        return false;
    }
}
