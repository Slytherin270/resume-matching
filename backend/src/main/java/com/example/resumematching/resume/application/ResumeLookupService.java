package com.example.resumematching.resume.application;

import com.example.resumematching.resume.api.ResumeLookup;
import com.example.resumematching.resume.api.ResumeSnapshot;
import com.example.resumematching.resume.infrastructure.ResumeRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumeLookupService implements ResumeLookup {
    private final ResumeRepository resumeRepository;

    public ResumeLookupService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeSnapshot getResumeForUser(UUID resumeId, UUID userId) {
        return resumeRepository.findById(resumeId)
                .filter(resume -> resume.getUserId().equals(userId))
                .map(resume -> new ResumeSnapshot(resume.getId(), resume.getUserId(), resume.getContent()))
                .orElseThrow(() -> new IllegalArgumentException("Resume not found or access denied"));
    }
}
