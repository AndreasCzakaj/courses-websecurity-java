package com.websecurity.app02;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;  // Minimum 256 bits for HS256

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // Validate secret key strength
        if (SECRET_KEY == null || SECRET_KEY.length() < 32) {
            throw new IllegalStateException(
                    "JWT secret must be at least 256 bits (32 characters)");
        }
        // Create SecretKey from string
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS512)  // Explicitly specify algorithm
                .compact();
    }

    public Claims extractAndValidateClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)  // This enforces HS512 based on the key type
                    .build()
                    // parseSignedClaims automatically validates signature and expiration
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("Token expired");
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("Invalid token format");
        } catch (SignatureException e) {
            throw new AuthenticationException("Invalid signature");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Token claims empty");
        }
    }

    static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}
