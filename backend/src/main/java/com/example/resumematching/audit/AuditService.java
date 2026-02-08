package com.example.resumematching.audit;

import com.example.resumematching.core.security.SecurityUtils;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional
    public AuditEntry record(UUID userId, String action, String details) {
        AuditEntry entry = new AuditEntry(UUID.randomUUID(), userId, action, details, Instant.now());
        return auditRepository.save(entry);
    }

    public Page<AuditEntry> listForCurrentUser(Pageable pageable) {
        return auditRepository.findByUserId(SecurityUtils.currentUserId(), pageable);
    }
}
