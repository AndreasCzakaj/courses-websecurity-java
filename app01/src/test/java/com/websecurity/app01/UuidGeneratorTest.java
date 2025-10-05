package com.websecurity.app01;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UuidGeneratorTest {

    private final UuidGenerator generator = new UuidGenerator();

    @Test
    void testUuidLength() {
        String uuid = generator.create();
        assertEquals(32, uuid.length(), "UUID should be 32 characters long");
    }

    @Test
    void testUuidContainsOnlyHexCharacters() {
        String uuid = generator.create();
        assertTrue(uuid.matches("[0-9a-f]{32}"),
            "UUID should only contain hex characters (0-9, a-f)");
    }

    @RepeatedTest(100)
    void testNoDuplicates() {
        Set<String> uuids = new HashSet<>();
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            String uuid = generator.create();
            uuids.add(uuid);
        }

        assertEquals(iterations, uuids.size(),
            "All generated UUIDs should be unique");
    }

    @RepeatedTest(100)
    void testAllHexCharactersUsed() {
        Set<Character> observedChars = new HashSet<>();
        Set<Character> expectedChars = Set.of(
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        );

        // Generate enough UUIDs to likely see all characters
        for (int i = 0; i < 100; i++) {
            String uuid = generator.create();
            for (char c : uuid.toCharArray()) {
                observedChars.add(c);
            }
        }

        assertEquals(expectedChars, observedChars,
            "All hex characters (0-9, a-f) should appear in generated UUIDs");
    }

    @RepeatedTest(5)
    void testNoPositionBias() {
        int samples = 10000;
        int positions = 32;

        // Track character frequency at each position
        Map<Integer, Map<Character, Integer>> positionFrequencies = new HashMap<>();
        for (int pos = 0; pos < positions; pos++) {
            positionFrequencies.put(pos, new HashMap<>());
        }

        // Generate samples
        for (int i = 0; i < samples; i++) {
            String uuid = generator.create();
            for (int pos = 0; pos < positions; pos++) {
                char c = uuid.charAt(pos);
                positionFrequencies.get(pos).merge(c, 1, Integer::sum);
            }
        }

        // Check each position for bias
        // Expected frequency for each char is samples/16 (since 16 possible hex chars)
        double expectedFreq = samples / 16.0;
        double tolerance = 0.2; // Allow 20% deviation

        for (int pos = 0; pos < positions; pos++) {
            Map<Character, Integer> charFreq = positionFrequencies.get(pos);

            for (char c = '0'; c <= '9'; c++) {
                int count = charFreq.getOrDefault(c, 0);
                double deviation = Math.abs(count - expectedFreq) / expectedFreq;
                assertTrue(deviation < tolerance,
                    String.format("Position %d: character '%c' appears %d times (expected ~%.0f, deviation %.2f%%)",
                        pos, c, count, expectedFreq, deviation * 100));
            }

            for (char c = 'a'; c <= 'f'; c++) {
                int count = charFreq.getOrDefault(c, 0);
                double deviation = Math.abs(count - expectedFreq) / expectedFreq;
                assertTrue(deviation < tolerance,
                    String.format("Position %d: character '%c' appears %d times (expected ~%.0f, deviation %.2f%%)",
                        pos, c, count, expectedFreq, deviation * 100));
            }
        }
    }
}
