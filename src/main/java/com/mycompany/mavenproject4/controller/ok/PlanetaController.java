package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.CreatePlanetaDto;
import com.mycompany.mavenproject4.dto.EditPlanetaDTO;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.PlanetaDatosConFotos;
import com.mycompany.mavenproject4.dto.PlanetaDatosSinFotos;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.servicios.PlanetaService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponseOk> editarPlaneta(
            @PathVariable Long id,
            @RequestBody EditPlanetaDTO request) {

        GeneralResponseOk response = planetaService.editarPlaneta(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseOk> eliminarPlaneta(
            @PathVariable Long id) {

        GeneralResponseOk response = planetaService.eliminarPlaneta(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/planetas/{id}/fotos")
    public ResponseEntity<GeneralResponseOk> obtenerFotosDelPlaneta(@PathVariable Long id) {
        Planeta planeta = planetaService.obtenerPlanetaConFotos(id);
        return ResponseEntity.ok(new GeneralResponseOk("Fotos del planeta obtenidas correctamente", planeta.getFotos()));

    }

    @GetMapping("/planetas/{id}")
    public ResponseEntity<GeneralResponseOk> obtenerDatosDelPlaneta(@PathVariable Long id) {
        
        PlanetaDatosSinFotos dto = planetaService.obtenerPlanetaSinFotos(id);        

        return ResponseEntity.ok(new GeneralResponseOk("Datos del planeta obtenidos correctamente", dto));
    }

    @GetMapping("/planetas")
    public GeneralResponseOk obtenerDatosDeVariosPlanetas(@RequestParam List<Long> ids) {
            List<PlanetaDatosSinFotos> dtos = planetaService.obtenerInfoDeVariosPlanetas(ids);                      
        
        return new GeneralResponseOk("Datos de los planetas obtenidos correctamente", dtos);
    }
    
        @GetMapping("/planetas/paginado")
    public ResponseEntity<GeneralResponseOk> obtenerPlanetasPaginados(Pageable pageable) {
        // Aquí se pasa el pageable al servicio para obtener la paginación
        Page<PlanetaDatosConFotos> planetasPage = planetaService.obtenerPlanetasPaginados(pageable);

        return ResponseEntity.ok(new GeneralResponseOk("Datos de planetas obtenidos correctamente", planetasPage));
    }
}


