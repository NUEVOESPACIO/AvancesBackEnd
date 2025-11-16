package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abm")
public class TodosLosUsuarios {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/todoslosusuarios")
    public ResponseEntity<?> obtenerUsuariosTodos() {

        var usuarios = userRepository.findAll().stream()
                .map(u -> Map.of(
                "username", u.getUsername(),
                "nombre", u.getNombre(),
                "apellido", u.getApellido(),
                "email", u.getEmail(),
                "rol",u.getRole()
        ))
                .toList();

        return ResponseEntity.ok(usuarios);
    }
}
