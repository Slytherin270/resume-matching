package com.example.resumematching.resume;

import java.util.UUID;

public record ResumeUploadedEvent(UUID resumeId, UUID userId, String fileName) {
}
