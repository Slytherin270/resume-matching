package com.example.resumematching.core.security;

import com.example.resumematching.user.domain.UserPrincipal;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static UUID currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new IllegalArgumentException("User not authenticated");
        }
        return principal.user().getId();
    }

    public static String currentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        return authentication.getName();
    }
}
