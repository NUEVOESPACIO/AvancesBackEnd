package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.PlanetaSimulacionRequest;
import com.mycompany.mavenproject4.dto.PlanetaSimulacionResponse;
import com.mycompany.mavenproject4.servicios.PlanetaSimulacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planetas-simulacion")
public class PlanetaSimulacionController {

    @Autowired
    private PlanetaSimulacionService planetaSimulacionService;

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public GeneralResponseOk create(@RequestBody PlanetaSimulacionRequest request) {
        return planetaSimulacionService.create(request);
    }

    // ============================
    // UPDATE
    // ============================
    @PutMapping("/{id}")
    public GeneralResponseOk update(
            @PathVariable Long id,
            @RequestBody PlanetaSimulacionRequest request) {
        return planetaSimulacionService.update(id, request);
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public GeneralResponseOk delete(@PathVariable Long id) {
        return planetaSimulacionService.delete(id);
    }

    // ============================
    // GET por simulación
    // ============================
    @GetMapping("/simulacion/{idSimulacion}")
    public List<PlanetaSimulacionResponse> getBySimulacion(
            @PathVariable Long idSimulacion) {
        return planetaSimulacionService.getBySimulacion(idSimulacion);
    }
}