package com.example.resumematching.resume;

import com.example.resumematching.resume.dto.ResumeResponse;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resumes")
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeMapper resumeMapper;

    public ResumeController(ResumeService resumeService, ResumeMapper resumeMapper) {
        this.resumeService = resumeService;
        this.resumeMapper = resumeMapper;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ResumeResponse uploadResume(@RequestParam("file") MultipartFile file) {
        return resumeMapper.toResponse(resumeService.uploadResume(file));
    }

    @GetMapping
    public Page<ResumeResponse> listResumes(@RequestParam(defaultValue = "0") @Min(0) int page,
                                            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return resumeService.listResumes(PageRequest.of(page, size)).map(resumeMapper::toResponse);
    }
}
