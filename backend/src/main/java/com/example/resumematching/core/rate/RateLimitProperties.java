package com.example.resumematching.core.rate;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resumematching.rate-limit")
public record RateLimitProperties(int requests, Duration window) {
}
