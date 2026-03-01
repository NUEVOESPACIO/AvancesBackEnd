package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.repository.PlanetaRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abm")
public class TodosLosPlanetas {

    @Autowired
    private PlanetaRepository planetaRepository;

    @GetMapping("/todoslosplanetas")
    public ResponseEntity<?> obtenerPlanetasTodos() {

        System.out.println("planetas ver");

        var planetas = planetaRepository.findAll().stream()
                .map(p -> Map.of(
                "id_planeta", p.getIdPlaneta(),
                "nombre", p.getNombre(),
                "nombre_cientifico", p.getNombre_cientifico(),
                "diametro", p.getDiametro(),
                "masa", p.getMasa(),
                "caracteristicas", p.getCaracteristicas(),
                "id_usuario", p.getUser().getId()
        ))
                .toList();

        return ResponseEntity.ok(planetas);
    }

}
