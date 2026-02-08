package com.example.resumematching.core.rate;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    private final RateLimitProperties properties;
    private final Map<String, RateWindow> windows = new ConcurrentHashMap<>();

    public RateLimitFilter(RateLimitProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String key = request.getRemoteAddr();
        Instant now = Instant.now();
        RateWindow window = windows.compute(key, (k, existing) -> {
            if (existing == null || existing.windowStart().plus(properties.window()).isBefore(now)) {
                return new RateWindow(now, 1);
            }
            return new RateWindow(existing.windowStart(), existing.count() + 1);
        });

        if (window.count() > properties.requests()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private record RateWindow(Instant windowStart, int count) {
    }
}
