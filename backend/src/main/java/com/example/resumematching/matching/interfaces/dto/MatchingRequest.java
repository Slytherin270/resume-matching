package com.example.resumematching.matching.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record MatchingRequest(
        @NotNull UUID resumeId,
        @NotBlank String jobDescription
) {
}
