package com.example.resumematching.auth.interfaces;

import com.example.resumematching.auth.application.AuthService;
import com.example.resumematching.auth.application.LoginCommand;
import com.example.resumematching.auth.application.RefreshCommand;
import com.example.resumematching.auth.application.RegisterCommand;
import com.example.resumematching.auth.application.TokenResult;
import com.example.resumematching.auth.interfaces.dto.AuthRequest;
import com.example.resumematching.auth.interfaces.dto.RefreshRequest;
import com.example.resumematching.auth.interfaces.dto.RegisterRequest;
import com.example.resumematching.auth.interfaces.dto.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@Valid @RequestBody RegisterRequest request) {
        TokenResult result = authService.register(new RegisterCommand(request.email(), request.password()));
        return toResponse(result);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody AuthRequest request) {
        TokenResult result = authService.login(new LoginCommand(request.email(), request.password()));
        return toResponse(result);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshRequest request) {
        TokenResult result = authService.refresh(new RefreshCommand(request.refreshToken()));
        return toResponse(result);
    }

    private TokenResponse toResponse(TokenResult result) {
        return new TokenResponse(result.accessToken(), result.refreshToken(), result.tokenType());
    }
}
