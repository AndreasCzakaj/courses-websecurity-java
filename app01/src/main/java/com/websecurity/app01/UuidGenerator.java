package com.websecurity.app01;

import java.security.SecureRandom;
import java.util.Random;

public class UuidGenerator {
    private static final Random RANDOM = new SecureRandom();

    public String create() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(createOne());
        }
        return sb.toString();
    }

    String createOne() {
        return Integer.toHexString(RANDOM.nextInt(16));
    }
}
