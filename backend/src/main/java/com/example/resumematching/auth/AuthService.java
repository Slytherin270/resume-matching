package com.example.resumematching.auth;

import com.example.resumematching.auth.dto.AuthRequest;
import com.example.resumematching.auth.dto.RegisterRequest;
import com.example.resumematching.auth.dto.RefreshRequest;
import com.example.resumematching.auth.dto.TokenResponse;
import com.example.resumematching.core.security.JwtProperties;
import com.example.resumematching.core.security.JwtService;
import com.example.resumematching.user.User;
import com.example.resumematching.user.UserService;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenStore refreshTokenStore;

    public AuthService(AuthenticationManager authenticationManager,
                       UserService userService,
                       JwtService jwtService,
                       JwtProperties jwtProperties,
                       RefreshTokenStore refreshTokenStore) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
        this.refreshTokenStore = refreshTokenStore;
    }

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        User user = userService.registerUser(request.email(), request.password());
        return issueTokens(user.getEmail());
    }

    public TokenResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        return issueTokens(authentication.getName());
    }

    public TokenResponse refresh(RefreshRequest request) {
        String subject = refreshTokenStore.resolveSubject(request.refreshToken());
        if (subject == null) {
            throw new IllegalArgumentException("Refresh token is invalid or expired");
        }
        refreshTokenStore.revoke(request.refreshToken());
        return issueTokens(subject);
    }

    private TokenResponse issueTokens(String subject) {
        String accessToken = jwtService.generateAccessToken(subject, Map.of("type", "access"));
        String refreshToken = jwtService.generateRefreshToken(subject, Map.of("type", "refresh"));
        refreshTokenStore.store(refreshToken, subject, jwtProperties.refreshTokenTtl());
        return new TokenResponse(accessToken, refreshToken, "Bearer");
    }
}
