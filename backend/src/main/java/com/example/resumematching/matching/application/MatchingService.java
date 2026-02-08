package com.example.resumematching.matching.application;

import com.example.resumematching.core.security.SecurityUtils;
import com.example.resumematching.resume.domain.Resume;
import com.example.resumematching.resume.infrastructure.ResumeRepository;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchingService {
    private final ResumeRepository resumeRepository;

    public MatchingService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "matches", key = "#request.resumeId().toString().concat(':').concat(#request.jobDescription().hashCode().toString())")
    public MatchResult match(MatchCommand command) {
        Resume resume = resumeRepository.findById(command.resumeId())
                .orElseThrow(() -> new IllegalArgumentException("Resume not found"));
        assertOwnership(resume.getUserId());

        Set<String> resumeKeywords = tokenize(resume.getContent());
        Set<String> jobKeywords = tokenize(command.jobDescription());
        long matched = jobKeywords.stream().filter(resumeKeywords::contains).count();
        int total = jobKeywords.size();
        int score = total == 0 ? 0 : (int) Math.round((matched * 100.0) / total);
        return new MatchResult(score, (int) matched, total);
    }

    private void assertOwnership(UUID userId) {
        UUID currentUserId = SecurityUtils.currentUserId();
        if (!currentUserId.equals(userId)) {
            throw new IllegalArgumentException("Resume does not belong to user");
        }
    }

    private Set<String> tokenize(String content) {
        return Arrays.stream(content.toLowerCase(Locale.ROOT).split("\\W+"))
                .filter(token -> token.length() > 2)
                .collect(Collectors.toSet());
    }
}
