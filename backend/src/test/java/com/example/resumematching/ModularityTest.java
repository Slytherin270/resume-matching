package com.example.resumematching;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {

    @Test
    void verifiesModularStructure() {
        ApplicationModules.of(ResumeMatchingApplication.class).verify();
    }
}
