package com.example.resumematching.audit.interfaces;

import com.example.resumematching.audit.application.AuditService;
import com.example.resumematching.audit.domain.AuditEntry;
import com.example.resumematching.audit.interfaces.dto.AuditResponse;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService auditService;

    @MockBean
    private AuditMapper auditMapper;

    @Test
    void listReturnsAuditEntries() throws Exception {
        UUID auditId = UUID.randomUUID();
        Instant createdAt = Instant.parse("2024-01-01T00:00:00Z");
        AuditEntry entry = new AuditEntry(auditId, UUID.randomUUID(), "LOGIN", "User logged in", createdAt);
        AuditResponse response = new AuditResponse(auditId, "LOGIN", "User logged in", createdAt);

        when(auditService.listForCurrentUser(eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(entry), PageRequest.of(0, 20), 1));
        when(auditMapper.toResponse(entry)).thenReturn(response);

        mockMvc.perform(get("/api/v1/audit")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(auditId.toString()))
                .andExpect(jsonPath("$.content[0].action").value("LOGIN"))
                .andExpect(jsonPath("$.content[0].details").value("User logged in"))
                .andExpect(jsonPath("$.content[0].createdAt").value("2024-01-01T00:00:00Z"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
