package com.example.resumematching.resume;

import com.example.resumematching.core.security.SecurityUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ResumeService(ResumeRepository resumeRepository, ApplicationEventPublisher eventPublisher) {
        this.resumeRepository = resumeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Resume uploadResume(MultipartFile file) {
        UUID userId = SecurityUtils.currentUserId();
        String content = readContent(file);
        Resume resume = new Resume(UUID.randomUUID(), userId, file.getOriginalFilename(), content, Instant.now());
        Resume saved = resumeRepository.save(resume);
        eventPublisher.publishEvent(new ResumeUploadedEvent(saved.getId(), userId, saved.getFileName()));
        return saved;
    }

    public Page<Resume> listResumes(Pageable pageable) {
        return resumeRepository.findByUserId(SecurityUtils.currentUserId(), pageable);
    }

    private String readContent(MultipartFile file) {
        try {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to read resume content");
        }
    }
}
