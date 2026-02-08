package com.example.resumematching.audit;

import com.example.resumematching.audit.dto.AuditResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditResponse toResponse(AuditEntry entry);
}
