package com.example.resumematching.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resumes")
@Getter
@NoArgsConstructor
public class Resume {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant createdAt;

    public Resume(UUID id, UUID userId, String fileName, String content, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.content = content;
        this.createdAt = createdAt;
    }
}
