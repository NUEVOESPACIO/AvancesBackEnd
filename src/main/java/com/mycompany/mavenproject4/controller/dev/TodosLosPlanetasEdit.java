
package com.mycompany.mavenproject4.controller.dev;

import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Hidden
@RestController
@RequestMapping("/abm")

public class TodosLosPlanetasEdit {

    @Autowired
    private PlanetaRepository planetaRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/actualizarplaneta")
    public ResponseEntity<?> actualizarPlaneta(@RequestBody Planeta planetaActualizado) {
        
        System.out.println("hola, actualizadno planeta");

        Optional<Planeta> optionalPlaneta = planetaRepository.findById(planetaActualizado.getIdPlaneta());

        if (optionalPlaneta.isEmpty()) {
            return ResponseEntity.badRequest().body("Planeta no encontrado");
        }

        Planeta planetaExistente = optionalPlaneta.get();

        // Actualizamos campos
        planetaExistente.setNombre(planetaActualizado.getNombre());
       
        planetaExistente.setDiametro(planetaActualizado.getDiametro());
        planetaExistente.setMasa(planetaActualizado.getMasa());
        planetaExistente.setCaracteristicas(planetaActualizado.getCaracteristicas());
        
                Long userId = planetaActualizado.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        planetaExistente.setUser(user);
        
                       

        planetaRepository.save(planetaExistente);

        return ResponseEntity.ok("Planeta actualizado correctamente");
    }
}
