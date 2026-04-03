package com.mycompany.mavenproject4.controller.dev;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.mavenproject4.repository.PlanetaRepository;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/abm")
public class EliminarPlaneta {

    @Autowired
    private PlanetaRepository planetaRepository;

    @DeleteMapping("/eliminarplaneta/{id}")
    public ResponseEntity<?> eliminarPlaneta(@PathVariable Long id) {
        
        System.out.println("entro a la part de eliminar planeta");

        planetaRepository.deleteById(id);

        return ResponseEntity.ok("Planeta eliminado correctamente");
    }
}
