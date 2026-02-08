package com.example.resumematching.resume.interfaces;

import com.example.resumematching.resume.domain.Resume;
import com.example.resumematching.resume.interfaces.dto.ResumeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeResponse toResponse(Resume resume);
}
