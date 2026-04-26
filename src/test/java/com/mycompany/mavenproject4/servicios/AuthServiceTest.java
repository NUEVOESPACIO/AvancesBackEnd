package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.AuthRequest;
import com.mycompany.mavenproject4.dto.AuthResponse;
import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.InvalidCredentialsException;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.security.JwtUtil;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_ok() {
        AuthRequest request = new AuthRequest("sebas", "1234");

        User user = new User();
        user.setId(1L);
        user.setUsername("sebas");
        user.setPassword("hashedPassword");

        Role role = new Role();
        role.setNombre(RoleName.ROLE_VISITANTE);
        user.setRole(role);

        when(userRepository.findByUsername("sebas"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("1234", "hashedPassword"))
                .thenReturn(true);

        when(jwtUtil.generateToken("sebas", "ROLE_VISITANTE"))
                .thenReturn("token123");

        AuthResponse response = authService.login(request);

        assertEquals("token123", response.getToken());
        assertEquals("ROLE_VISITANTE", response.getRol());
        assertEquals(1L, response.getId());
        assertEquals("sebas", response.getUsername());
    }

    @Test
    void login_user_not_found() {
        AuthRequest request = new AuthRequest("sebas", "1234");

        when(userRepository.findByUsername("sebas"))
                .thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(request);
        });
    }

    @Test
    void login_wrong_password() {
        AuthRequest request = new AuthRequest("sebas", "1234");

        User user = new User();
        user.setUsername("sebas");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername("sebas"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("1234", "hashedPassword"))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(request);
        });
    }
}    

