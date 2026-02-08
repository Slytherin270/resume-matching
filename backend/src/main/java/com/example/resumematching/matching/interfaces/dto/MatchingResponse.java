package com.example.resumematching.matching.interfaces.dto;

public record MatchingResponse(
        int score,
        int matchedKeywords,
        int totalKeywords
) {
}
