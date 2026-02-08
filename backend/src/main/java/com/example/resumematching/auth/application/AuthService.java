package com.example.resumematching.auth.application;

import com.example.resumematching.auth.infrastructure.RefreshTokenStore;
import com.example.resumematching.core.security.JwtProperties;
import com.example.resumematching.core.security.JwtService;
import com.example.resumematching.user.application.UserService;
import com.example.resumematching.user.domain.User;
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
    public TokenResult register(RegisterCommand command) {
        User user = userService.registerUser(command.email(), command.password());
        return issueTokens(user.getEmail());
    }

    public TokenResult login(LoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.email(), command.password())
        );
        return issueTokens(authentication.getName());
    }

    public TokenResult refresh(RefreshCommand command) {
        String subject = refreshTokenStore.resolveSubject(command.refreshToken());
        if (subject == null) {
            throw new IllegalArgumentException("Refresh token is invalid or expired");
        }
        refreshTokenStore.revoke(command.refreshToken());
        return issueTokens(subject);
    }

    private TokenResult issueTokens(String subject) {
        String accessToken = jwtService.generateAccessToken(subject, Map.of("type", "access"));
        String refreshToken = jwtService.generateRefreshToken(subject, Map.of("type", "refresh"));
        refreshTokenStore.store(refreshToken, subject, jwtProperties.refreshTokenTtl());
        return new TokenResult(accessToken, refreshToken, "Bearer");
    }
}
