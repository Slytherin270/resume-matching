package com.example.resumematching.matching.interfaces;

import com.example.resumematching.matching.application.MatchResult;
import com.example.resumematching.matching.application.MatchingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchingController.class)
@AutoConfigureMockMvc(addFilters = false)
class MatchingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MatchingService matchingService;

    @Test
    void runMatchReturnsScores() throws Exception {
        when(matchingService.match(any())).thenReturn(new MatchResult(80, 8, 10));

        String payload = objectMapper.writeValueAsString(new TestMatchingRequest(UUID.randomUUID(), "java spring boot"));

        mockMvc.perform(post("/api/v1/matching/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(80))
                .andExpect(jsonPath("$.matchedKeywords").value(8))
                .andExpect(jsonPath("$.totalKeywords").value(10));
    }

    private record TestMatchingRequest(UUID resumeId, String jobDescription) {
    }
}
