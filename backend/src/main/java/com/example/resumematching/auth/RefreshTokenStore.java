package com.example.resumematching.auth;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenStore {
    private final StringRedisTemplate redisTemplate;

    public RefreshTokenStore(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void store(String refreshToken, String subject, Duration ttl) {
        redisTemplate.opsForValue().set(key(refreshToken), subject, ttl);
    }

    public String resolveSubject(String refreshToken) {
        return redisTemplate.opsForValue().get(key(refreshToken));
    }

    public void revoke(String refreshToken) {
        redisTemplate.delete(key(refreshToken));
    }

    private String key(String refreshToken) {
        return "refresh:" + refreshToken;
    }
}
