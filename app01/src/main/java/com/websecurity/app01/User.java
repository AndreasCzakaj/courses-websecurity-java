package com.websecurity.app01;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String role;

    @Column(length = 32, unique = true)
    private String safeUserId;

    public User() {}

    public User(String username, String email, String password, String firstName, String lastName, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String username, String email, String password, String firstName, String lastName, String role, String safeUserId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.safeUserId = safeUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSafeUserId() {
        return safeUserId;
    }

    public void setSafeUserId(String safeUserId) {
        this.safeUserId = safeUserId;
    }


    // Security tracking fields
    @Column(name = "failed_login_attempts")
    private int failedLoginAttempts = 0;

    @Column(name = "locked_until")
    private Instant lockedUntil;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "security_version")
    private int securityVersion = 1;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @Column(name = "password_changed_at")
    private Instant passwordChangedAt;

    // Password reset fields
    @Column(name = "reset_token_hash")
    private String resetTokenHash;

    @Column(name = "reset_token_expiry")
    private Instant resetTokenExpiry;

    @Column(name = "reset_token_used")
    private boolean resetTokenUsed;

    // Audit fields
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    // Relationships
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MfaSecret mfaSecret;

    // Constructor, getters, setters...

    public boolean isAccountNonLocked() {
        return accountNonLocked &&
                (lockedUntil == null || lockedUntil.isBefore(Instant.now()));
    }

    public boolean isPasswordExpired() {
        if (passwordChangedAt == null) return true;
        return passwordChangedAt.plus(Duration.ofDays(90)).isBefore(Instant.now());
    }
}