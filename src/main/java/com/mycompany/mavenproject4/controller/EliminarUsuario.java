package com.mycompany.mavenproject4.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.mavenproject4.servicios.UserService;

@RestController
@RequestMapping("/abm")
public class EliminarUsuario {

    @Autowired
    private UserService userService;

    @DeleteMapping("/eliminarusuario/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        
        System.out.println("entro a la part de eliminar");

        userService.eliminarUsuario(id);

        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
