package com.websecurity.app01;

import java.security.SecureRandom;
import java.util.Base64;

public class NonceGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String generateNonce() {
        byte[] bytes = new byte[16]; // 128 bits
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
