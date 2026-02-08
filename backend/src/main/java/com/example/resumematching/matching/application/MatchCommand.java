package com.example.resumematching.matching.application;

import java.util.UUID;

public record MatchCommand(UUID resumeId, String jobDescription) {
}
