package com.mycompany.mavenproject4.controller.dev;

import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Hidden
@RestController
@RequestMapping("/abm")

public class TodosLosUsuariosEdit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/actualizarusuario")
    public ResponseEntity<?> actualizarUsuario(@RequestBody User userActualizado) {
        
        System.out.println("hola");

        Optional<User> optionalUser = userRepository.findById(userActualizado.getId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        User userExistente = optionalUser.get();

        // Actualizamos campos
        userExistente.setNombre(userActualizado.getNombre());
        userExistente.setApellido(userActualizado.getApellido());
        userExistente.setEmail(userActualizado.getEmail());
        userExistente.setUsername(userActualizado.getUsername());

        // 🔹 Actualizar rol correctamente desde BD
        if (userActualizado.getRole() != null) {
            Optional<Role> roleOptional = roleRepository.findById(userActualizado.getRole().getId());
            roleOptional.ifPresent(userExistente::setRole);
        }

        userRepository.save(userExistente);

        return ResponseEntity.ok("Usuario actualizado correctamente");
    }
}
