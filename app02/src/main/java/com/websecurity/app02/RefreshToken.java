package com.websecurity.app02;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private boolean revoked = false;

    @CreatedDate
    private Instant createdDate;

    Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    String getToken() {
        return token;
    }

    void setToken(final String token) {
        this.token = token;
    }

    String getUsername() {
        return username;
    }

    void setUsername(final String username) {
        this.username = username;
    }

    Instant getExpiryDate() {
        return expiryDate;
    }

    void setExpiryDate(final Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    boolean isRevoked() {
        return revoked;
    }

    void setRevoked(final boolean revoked) {
        this.revoked = revoked;
    }

    Instant getCreatedDate() {
        return createdDate;
    }

    void setCreatedDate(final Instant createdDate) {
        this.createdDate = createdDate;
    }
}