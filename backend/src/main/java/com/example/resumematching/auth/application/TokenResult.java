package com.example.resumematching.auth.application;

public record TokenResult(String accessToken, String refreshToken, String tokenType) {
}
