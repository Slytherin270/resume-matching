package com.example.resumematching.resume.infrastructure;

import com.example.resumematching.resume.domain.Resume;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    Page<Resume> findByUserId(UUID userId, Pageable pageable);
}
