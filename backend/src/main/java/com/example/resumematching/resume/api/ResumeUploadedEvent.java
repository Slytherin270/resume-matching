package com.example.resumematching.resume.api;

import java.util.UUID;

public record ResumeUploadedEvent(UUID resumeId, UUID userId, String fileName) {
}
