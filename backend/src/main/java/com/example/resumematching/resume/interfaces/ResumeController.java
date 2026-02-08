package com.example.resumematching.resume.interfaces;

import com.example.resumematching.resume.application.ResumeService;
import com.example.resumematching.resume.application.ResumeUploadCommand;
import com.example.resumematching.resume.interfaces.dto.ResumeResponse;
import jakarta.validation.constraints.Min;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        String content = readContent(file);
        ResumeUploadCommand command = new ResumeUploadCommand(file.getOriginalFilename(), content);
        return resumeMapper.toResponse(resumeService.uploadResume(command));
    }

    @GetMapping
    public Page<ResumeResponse> listResumes(@RequestParam(defaultValue = "0") @Min(0) int page,
                                            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return resumeService.listResumes(PageRequest.of(page, size)).map(resumeMapper::toResponse);
    }

    private String readContent(MultipartFile file) {
        try {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to read resume content");
        }
    }
}
