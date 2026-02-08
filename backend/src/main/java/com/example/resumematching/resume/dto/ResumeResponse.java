package com.example.resumematching.resume.dto;

import java.time.Instant;
import java.util.UUID;

public record ResumeResponse(
        UUID id,
        String fileName,
        Instant createdAt
) {
}
