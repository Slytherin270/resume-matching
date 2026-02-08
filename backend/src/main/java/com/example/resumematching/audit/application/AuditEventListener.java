package com.example.resumematching.audit.application;

import com.example.resumematching.resume.domain.ResumeUploadedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {
    private final AuditService auditService;

    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @EventListener
    public void onResumeUploaded(ResumeUploadedEvent event) {
        String details = "Resume uploaded: " + event.fileName();
        auditService.record(event.userId(), "RESUME_UPLOADED", details);
    }
}
