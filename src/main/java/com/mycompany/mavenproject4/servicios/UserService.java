package com.mycompany.mavenproject4.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.repository.RoleRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================
    // CREAR USUARIO
    // =========================
    public User crearUsuario(User user) {

        // Validar username repetido
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }

        // Validar email repetido
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya existe");
        }

        // Buscar el rol desde la base
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Encriptar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar rol completo
        user.setRole(role);

        return userRepository.save(user);
    }

    // =========================
    // OBTENER USUARIO POR ID
    // =========================
    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }

    // =========================
    // ELIMINAR USUARIO
    // =========================
    public void eliminarUsuario(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        userRepository.deleteById(id);
    }

}