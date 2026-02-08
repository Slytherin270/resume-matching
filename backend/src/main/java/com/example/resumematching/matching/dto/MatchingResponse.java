package com.example.resumematching.matching.dto;

public record MatchingResponse(
        int score,
        int matchedKeywords,
        int totalKeywords
) {
}
