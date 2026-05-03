package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.servicios.SimulacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulaciones")
public class SimulacionController {

    @Autowired
    private SimulacionService simulacionService;

    // ============================
    // Crear simulación
    // ============================
    @PostMapping
    public GeneralResponseOk createSimulacion(@RequestBody SimulacionRequestDTO requestDTO) {
        return simulacionService.createSimulacion(requestDTO);
    }

    // ============================
    // Editar simulación
    // ============================
    @PutMapping("/{id}")
    public GeneralResponseOk editSimulacion(
            @PathVariable Long id,
            @RequestBody SimulacionRequestDTO requestDTO) {
        return simulacionService.editSimulacion(id, requestDTO);
    }

    // ============================
    // Listado paginado (light)
    // ============================
    @GetMapping
    public Page<SimulacionLightDTO> listSimulaciones(Pageable pageable) {
        return simulacionService.listSimulaciones(pageable);
    }

    // ============================
    // Obtener una simulación (heavy)
    // ============================
    @GetMapping("/{id}")
    public SimulacionHeavyDTO getSimulacionById(@PathVariable Long id) {
        return simulacionService.getSimulacionById(id);
    }

    // ============================
    // Eliminar simulación
    // ============================
    @DeleteMapping("/{id}")
    public GeneralResponseOk deleteSimulacion(@PathVariable Long id) {
        return simulacionService.deleteSimulacion(id);
    }
}