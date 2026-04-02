package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.AuthRequest;
import com.mycompany.mavenproject4.dto.AuthResponse;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.AuthException;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest authRequest) {

        logger.info("Intento de login: {}", authRequest.getUsername());

        // Buscar usuario
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AuthException("Credenciales inválidas"));

        // Validar contraseña
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            logger.warn("Password incorrecta para: {}", authRequest.getUsername());
            throw new AuthException("Credenciales inválidas");
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().getNombre());

        logger.info("Login exitoso: {}", user.getUsername());

        // Construir DTO de respuesta
        return new AuthResponse(
                token,
                user.getRole().getNombre().name(),
                user.getId(),
                user.getUsername()
        );
    }
}