package com.mycompany.mavenproject4.config;

import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.RoleRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {

        return args -> {

            logger.info("Iniciando carga de datos...");

            // 🔹 Crear roles si no existen
            Role adminRole = createRoleIfNotFound(roleRepository, RoleName.ROLE_ADMIN);
            Role userRole = createRoleIfNotFound(roleRepository, RoleName.ROLE_VISITANTE);
            Role fisicoRole = createRoleIfNotFound(roleRepository, RoleName.ROLE_FISICO);
            Role astronomoRole = createRoleIfNotFound(roleRepository, RoleName.ROLE_ASTRONOMO);

            // 🔹 Crear usuarios si no existen
            createUserIfNotFound(userRepository, passwordEncoder,
                    "admin", "Admin", "Principal", "admin123", "admin@test.com", adminRole);

            createUserIfNotFound(userRepository, passwordEncoder,
                    "user1", "Juan", "Perez", "1234", "user1@test.com", userRole);

            createUserIfNotFound(userRepository, passwordEncoder,
                    "user2", "Ana", "Gomez", "1234", "user2@test.com", fisicoRole);

            createUserIfNotFound(userRepository, passwordEncoder,
                    "user3", "Luis", "Martinez", "1234", "user3@test.com", astronomoRole);

            logger.info("Carga de datos finalizada.");
        };
    }

    private Role createRoleIfNotFound(RoleRepository roleRepository, RoleName roleName) {

        return roleRepository.findByNombre(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setNombre(roleName);
                    logger.info("Creando rol: {}", roleName);
                    return roleRepository.save(role);
                });
    }

    private void createUserIfNotFound(UserRepository userRepository,
                                      PasswordEncoder passwordEncoder,
                                      String username,
                                      String nombre,
                                      String apellido,
                                      String rawPassword,
                                      String email,
                                      Role role) {

        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        user -> logger.info("Usuario ya existe: {}", username),
                        () -> {
                            User user = new User();
                            user.setUsername(username);
                            user.setNombre(nombre);
                            user.setApellido(apellido);
                            user.setEmail(email);

                            // 🔥 CAMPOS OBLIGATORIOS
                            user.setFoto(new byte[]{1}); // dummy
                            user.setMimeType("image/png");

                            user.setPassword(passwordEncoder.encode(rawPassword));
                            user.setRole(role);

                            userRepository.save(user);
                            logger.info("Usuario creado: {}", username);
                        }
                );
    }
}