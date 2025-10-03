package com.websecurity.testing;

public class Validation {
    String checkAndSanitize(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }
        String out = input.trim();

        if (out.length() < 1) {
            throw new IllegalArgumentException("Input must be at least 1 char");
        }

        return out;
    }
}
