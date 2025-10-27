package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.dto.AuthRequest;
import com.mycompany.mavenproject4.dto.AuthResponse;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil,UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authRequest) {
        
                User user = userRepository.findByUsername(authRequest.getUsername()).orElse(null);

                
        //if ("usuario".equals(authRequest.getUsername()) && ("password".equals(authRequest.getPassword()))) {
            
                    if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                        
                        
                    
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {

            return ResponseEntity.status(401).build();

        }
    }

}
