package com.mycompany.mavenproject4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.UserService;

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
