package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.CreatePlanetaDto;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.servicios.PlanetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planetas")
public class PlanetaController {

    private final PlanetaService planetaService;

    public PlanetaController(PlanetaService planetaService) {
        this.planetaService = planetaService;
    }

    @PostMapping
    public ResponseEntity<GeneralResponseOk> createPlaneta(
            @RequestBody CreatePlanetaDto request) {

        GeneralResponseOk response = planetaService.createPlaneta(request);

        return ResponseEntity.ok(response);
    }
}