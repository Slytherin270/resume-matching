package com.example.resumematching.audit.interfaces;

import com.example.resumematching.audit.domain.AuditEntry;
import com.example.resumematching.audit.interfaces.dto.AuditResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditResponse toResponse(AuditEntry entry);
}
