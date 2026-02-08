package com.example.resumematching;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

import static org.assertj.core.api.Assertions.assertThat;

class ResumeMatchingApplicationUnitTest {

    @Test
    void applicationHasExpectedAnnotations() {
        assertThat(ResumeMatchingApplication.class.isAnnotationPresent(SpringBootApplication.class))
                .as("@SpringBootApplication should be present")
                .isTrue();
        assertThat(ResumeMatchingApplication.class.isAnnotationPresent(Modulithic.class))
                .as("@Modulithic should be present")
                .isTrue();
    }
}
