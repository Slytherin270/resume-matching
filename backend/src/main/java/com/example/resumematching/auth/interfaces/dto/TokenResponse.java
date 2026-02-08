package com.example.resumematching.auth.interfaces.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}
