package com.example.resumematching.matching.application;

import com.example.resumematching.core.security.SecurityUtils;
import com.example.resumematching.resume.api.ResumeLookup;
import com.example.resumematching.resume.api.ResumeSnapshot;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchingService {
    private final ResumeLookup resumeLookup;

    public MatchingService(ResumeLookup resumeLookup) {
        this.resumeLookup = resumeLookup;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "matches", key = "#request.resumeId().toString().concat(':').concat(#request.jobDescription().hashCode().toString())")
    public MatchResult match(MatchCommand command) {
        ResumeSnapshot resume = resumeLookup.getResumeForUser(command.resumeId(), SecurityUtils.currentUserId());

        Set<String> resumeKeywords = tokenize(resume.getContent());
        Set<String> jobKeywords = tokenize(command.jobDescription());
        long matched = jobKeywords.stream().filter(resumeKeywords::contains).count();
        int total = jobKeywords.size();
        int score = total == 0 ? 0 : (int) Math.round((matched * 100.0) / total);
        return new MatchResult(score, (int) matched, total);
    }

    private Set<String> tokenize(String content) {
        return Arrays.stream(content.toLowerCase(Locale.ROOT).split("\\W+"))
                .filter(token -> token.length() > 2)
                .collect(Collectors.toSet());
    }
}
