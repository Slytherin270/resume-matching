package com.example.resumematching.resume.interfaces.dto;

import java.time.Instant;
import java.util.UUID;

public record ResumeResponse(
        UUID id,
        String fileName,
        Instant createdAt
) {
}
