package com.mycompany.mavenproject4.controller.ok;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject4.dto.AuthRequest;
import com.mycompany.mavenproject4.dto.AuthResponse;
import com.mycompany.mavenproject4.exception.GlobalExceptionHandler;
import com.mycompany.mavenproject4.exception.InvalidCredentialsException;
import com.mycompany.mavenproject4.security.JwtAuthenticationFilter;
import com.mycompany.mavenproject4.security.JwtUtil;
import com.mycompany.mavenproject4.servicios.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import org.springframework.context.annotation.Import;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoginSuccessfully() throws Exception {

        AuthRequest request = new AuthRequest();
        request.setUsername("sebas");
        request.setPassword("1234");

        AuthResponse response = new AuthResponse(
                "fake-jwt-token",
                "USER",
                1L,
                "sebas"
        );

        when(authService.login(org.mockito.ArgumentMatchers.any(AuthRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    void shouldReturn401WhenCredentialsAreInvalid() throws Exception {

        AuthRequest request = new AuthRequest();
        request.setUsername("sebas");
        request.setPassword("wrong-password");

        when(authService.login(org.mockito.ArgumentMatchers.any(AuthRequest.class)))
                .thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"))
                .andExpect(jsonPath("$.message").value("Credenciales inválidas"));
    }
}
