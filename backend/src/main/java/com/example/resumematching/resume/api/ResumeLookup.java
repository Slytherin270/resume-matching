package com.example.resumematching.resume.api;

import java.util.UUID;

public interface ResumeLookup {
    ResumeSnapshot getResumeForUser(UUID resumeId, UUID userId);
}
