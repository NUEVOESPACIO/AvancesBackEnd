package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.dto.AuthRequest;
import com.mycompany.mavenproject4.dto.AuthResponse;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.security.JwtUtil;
import java.util.List;
import java.util.stream.Collectors;
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
                        
               String role = user.getRole().getNombre();
                    
            String token = jwtUtil.generateToken(authRequest.getUsername(),role);
               System.out.println("Autenticado OK" + user.getUsername()+" "+user.getNombre()+" "+user.getApellido()+" "+user.getRole().getNombre());
            return ResponseEntity.ok(new AuthResponse(token,user.getRole().getNombre(),user.getId()));
        } else {

            return ResponseEntity.status(401).build();

        }
    }

}
