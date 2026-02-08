package com.example.resumematching.auth.interfaces;

import com.example.resumematching.auth.application.AuthService;
import com.example.resumematching.auth.application.TokenResult;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void registerReturnsTokenResponse() throws Exception {
        when(authService.register(any())).thenReturn(new TokenResult("access", "refresh", "Bearer"));

        String payload = objectMapper.writeValueAsString(new TestRegisterRequest("user@example.com", "password123"));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("access"))
                .andExpect(jsonPath("$.refreshToken").value("refresh"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void loginReturnsTokenResponse() throws Exception {
        when(authService.login(any())).thenReturn(new TokenResult("access", "refresh", "Bearer"));

        String payload = objectMapper.writeValueAsString(new TestAuthRequest("user@example.com", "password123"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access"))
                .andExpect(jsonPath("$.refreshToken").value("refresh"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void refreshReturnsTokenResponse() throws Exception {
        when(authService.refresh(any())).thenReturn(new TokenResult("new-access", "new-refresh", "Bearer"));

        String payload = objectMapper.writeValueAsString(new TestRefreshRequest("refresh-token"));

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access"))
                .andExpect(jsonPath("$.refreshToken").value("new-refresh"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    private record TestRegisterRequest(String email, String password) {
    }

    private record TestAuthRequest(String email, String password) {
    }

    private record TestRefreshRequest(String refreshToken) {
    }
}
