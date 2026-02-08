package com.example.resumematching.resume.api;

import java.util.UUID;

public record ResumeSnapshot(UUID id, UUID userId, String content) {
}
