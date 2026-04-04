package com.mycompany.mavenproject4.controller.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.repository.UserRepository;



@RestController
@RequestMapping("/abm")
public class CrearPlaneta {

    @Autowired
    private PlanetaRepository planetaRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/crearplaneta")
    public Planeta crearPlaneta(@RequestBody Planeta planeta) {

        System.out.println("planeta");

        Long userId = planeta.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        planeta.setUser(user);

        return planetaRepository.save(planeta);
    }
}
