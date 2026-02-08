package com.example.resumematching.resume;

import com.example.resumematching.resume.dto.ResumeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeResponse toResponse(Resume resume);
}
