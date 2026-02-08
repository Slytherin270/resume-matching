package com.example.resumematching.audit.interfaces;

import com.example.resumematching.audit.application.AuditService;
import com.example.resumematching.audit.interfaces.dto.AuditResponse;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {
    private final AuditService auditService;
    private final AuditMapper auditMapper;

    public AuditController(AuditService auditService, AuditMapper auditMapper) {
        this.auditService = auditService;
        this.auditMapper = auditMapper;
    }

    @GetMapping
    public Page<AuditResponse> list(@RequestParam(defaultValue = "0") @Min(0) int page,
                                    @RequestParam(defaultValue = "20") @Min(1) int size) {
        return auditService.listForCurrentUser(PageRequest.of(page, size)).map(auditMapper::toResponse);
    }
}
