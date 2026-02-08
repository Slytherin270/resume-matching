package com.example.resumematching.resume.domain;

import java.util.UUID;

public record ResumeUploadedEvent(UUID resumeId, UUID userId, String fileName) {
}
