package com.mycompany.mavenproject4.controller.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.UserService;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/abm")
public class CrearUsuario {

    @Autowired
    private UserService userService;

    @PostMapping("/crearusuario")
    public User crearUsuario(@RequestBody User user) {
        
        return userService.crearUsuario(user);
        
        
        
    }
}
