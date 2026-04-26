package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import com.mycompany.mavenproject4.entidades.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    // 🔧 Helper para crear Role persistido
    private Role crearRole() {
        Role role = new Role();
        role.setNombre(RoleName.ROLE_VISITANTE);
        return entityManager.persist(role);
    }

    // 🔧 Helper para crear User persistido
    private User crearUser(String username) {
        Role role = crearRole();

        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@mail.com");
        user.setPassword("1234");

        // 🔥 TODOS los obligatorios
        user.setNombre("Sebas");
        user.setApellido("Perez");
        user.setFoto("foto.jpg".getBytes());
        user.setMimeType("mime_dummy");
        user.setRole(role);

        return entityManager.persist(user);
    }

    @Test
    @DisplayName("Debe guardar y encontrar usuario por username")
    void findByUsername_ok() {

        // Arrange
        crearUser("sebas");

        // Act
        Optional<User> result = userRepository.findByUsername("sebas");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("sebas", result.get().getUsername());
    }

    @Test
    @DisplayName("Debe devolver vacío si el username no existe")
    void findByUsername_notFound() {

        Optional<User> result = userRepository.findByUsername("noexiste");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debe verificar existencia por username")
    void existsByUsername_ok() {

        crearUser("sebas");

        boolean exists = userRepository.existsByUsername("sebas");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Debe verificar existencia por email")
    void existsByEmail_ok() {

        crearUser("sebas");

        boolean exists = userRepository.existsByEmail("sebas@mail.com");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Debe eliminar usuario por id")
    void deleteById_ok() {

        User user = crearUser("sebas");

        userRepository.deleteById(user.getId());

        Optional<User> result = userRepository.findById(user.getId());

        assertTrue(result.isEmpty());
    }
}
