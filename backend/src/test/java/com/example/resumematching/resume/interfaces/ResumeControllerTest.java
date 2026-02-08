package com.example.resumematching.resume.interfaces;

import com.example.resumematching.resume.application.ResumeService;
import com.example.resumematching.resume.domain.Resume;
import com.example.resumematching.resume.interfaces.dto.ResumeResponse;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResumeController.class)
@AutoConfigureMockMvc(addFilters = false)
class ResumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResumeService resumeService;

    @MockBean
    private ResumeMapper resumeMapper;

    @Test
    void uploadResumeReturnsCreatedResume() throws Exception {
        UUID resumeId = UUID.randomUUID();
        Instant createdAt = Instant.parse("2024-01-01T00:00:00Z");
        Resume resume = new Resume(resumeId, UUID.randomUUID(), "resume.txt", "content", createdAt);
        ResumeResponse response = new ResumeResponse(resumeId, "resume.txt", createdAt);

        when(resumeService.uploadResume(any())).thenReturn(resume);
        when(resumeMapper.toResponse(resume)).thenReturn(response);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "resume.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "resume content".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/resumes/upload").file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(resumeId.toString()))
                .andExpect(jsonPath("$.fileName").value("resume.txt"))
                .andExpect(jsonPath("$.createdAt").value("2024-01-01T00:00:00Z"));
    }

    @Test
    void listResumesReturnsPagedResults() throws Exception {
        UUID resumeId = UUID.randomUUID();
        Instant createdAt = Instant.parse("2024-01-01T00:00:00Z");
        Resume resume = new Resume(resumeId, UUID.randomUUID(), "resume.txt", "content", createdAt);
        ResumeResponse response = new ResumeResponse(resumeId, "resume.txt", createdAt);

        when(resumeService.listResumes(eq(PageRequest.of(0, 10))))
                .thenReturn(new PageImpl<>(List.of(resume), PageRequest.of(0, 10), 1));
        when(resumeMapper.toResponse(resume)).thenReturn(response);

        mockMvc.perform(get("/api/v1/resumes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(resumeId.toString()))
                .andExpect(jsonPath("$.content[0].fileName").value("resume.txt"))
                .andExpect(jsonPath("$.content[0].createdAt").value("2024-01-01T00:00:00Z"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
