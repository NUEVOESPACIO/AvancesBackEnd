package com.mycompany.mavenproject4.integration;

import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.repository.RoleRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔥 helper PRO: evita duplicados
    private Role obtenerRole() {
        Optional<Role> roleOpt = roleRepository.findByNombre(RoleName.ROLE_VISITANTE);

        if (roleOpt.isPresent()) {
            return roleOpt.get();
        }

        Role role = new Role();
        role.setNombre(RoleName.ROLE_VISITANTE);
        return roleRepository.save(role);
    }

    @Test
    void login_real_ok() throws Exception {

        // 🔥 usar helper en vez de crear siempre
        Role role = obtenerRole();

        // Arrange (DB real)
        User user = new User();
        String name = "sebas+" + System.currentTimeMillis();
        String email =name+"@mail.com";
        user.setEmail(email);
        user.setUsername(name);
        
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setNombre("Sebas");
        user.setApellido("Perez");
        user.setMimeType("dummy");
        user.setFoto("foto.jpg".getBytes());

        user.setRole(role);

        userRepository.save(user);

        // Act + Assert (flujo completo)
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "username": "sebas",
                        "password": "1234"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("sebas"))
                .andExpect(jsonPath("$.rol").value("ROLE_VISITANTE"));
    }
}