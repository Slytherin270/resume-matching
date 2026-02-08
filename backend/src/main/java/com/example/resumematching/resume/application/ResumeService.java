package com.example.resumematching.resume.application;

import com.example.resumematching.core.security.SecurityUtils;
import com.example.resumematching.resume.domain.Resume;
import com.example.resumematching.resume.domain.ResumeUploadedEvent;
import com.example.resumematching.resume.infrastructure.ResumeRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ResumeService(ResumeRepository resumeRepository, ApplicationEventPublisher eventPublisher) {
        this.resumeRepository = resumeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Resume uploadResume(ResumeUploadCommand command) {
        UUID userId = SecurityUtils.currentUserId();
        Resume resume = new Resume(UUID.randomUUID(), userId, command.fileName(), command.content(), Instant.now());
        Resume saved = resumeRepository.save(resume);
        eventPublisher.publishEvent(new ResumeUploadedEvent(saved.getId(), userId, saved.getFileName()));
        return saved;
    }

    public Page<Resume> listResumes(Pageable pageable) {
        return resumeRepository.findByUserId(SecurityUtils.currentUserId(), pageable);
    }

}
