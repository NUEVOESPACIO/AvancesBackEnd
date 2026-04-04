package com.mycompany.mavenproject4.controller.dev;

import com.mycompany.mavenproject4.repository.RoleRepository;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/abm")
public class TodosLosRoles {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/todoslosroles")
    public ResponseEntity<?> obtenerTodosLosRoles() {

        var roles = roleRepository.findAll().stream()
                .map(r -> Map.of(
                        "id", r.getId(),
                        "nombre", r.getNombre()
                ))
                .toList();

        return ResponseEntity.ok(roles);
    }
}
