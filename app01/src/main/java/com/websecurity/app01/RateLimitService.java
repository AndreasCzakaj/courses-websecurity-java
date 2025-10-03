package com.websecurity.app01;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class RateLimitService {

    private final Map<String, RateLimitBucket> buckets = new ConcurrentHashMap<>();

    public boolean isAllowed(String key, String action, int maxAttempts, Duration timeWindow) {
        String bucketKey = key + ":" + action;
        RateLimitBucket bucket = buckets.computeIfAbsent(bucketKey,
                k -> new RateLimitBucket(maxAttempts, timeWindow));

        return bucket.tryConsume();
    }

    @Scheduled(fixedRate = 60000) // Clean up every minute
    public void cleanupExpiredBuckets() {
        Instant now = Instant.now();
        buckets.entrySet().removeIf(entry -> entry.getValue().isExpired(now));
    }

    private static class RateLimitBucket {
        private final int maxAttempts;
        private final Duration timeWindow;
        private final Queue<Instant> attempts = new ConcurrentLinkedQueue<>();

        public RateLimitBucket(int maxAttempts, Duration timeWindow) {
            this.maxAttempts = maxAttempts;
            this.timeWindow = timeWindow;
        }

        public synchronized boolean tryConsume() {
            Instant now = Instant.now();
            Instant cutoff = now.minus(timeWindow);

            // Remove old attempts
            attempts.removeIf(attempt -> attempt.isBefore(cutoff));

            if (attempts.size() < maxAttempts) {
                attempts.offer(now);
                return true;
            }

            return false;
        }

        public boolean isExpired(Instant now) {
            return attempts.isEmpty() ||
                    attempts.peek().plus(timeWindow).isBefore(now);
        }
    }
}
