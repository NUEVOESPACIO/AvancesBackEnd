package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.CreatePlanetaDto;
import com.mycompany.mavenproject4.dto.EditPlanetaDTO;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.PlanetaDatosSinFotos;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.servicios.PlanetaService;
import java.util.ArrayList;
import java.util.List;
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
        Planeta planeta = planetaService.obtenerPlanetaSinFotos(id);
        PlanetaDatosSinFotos dto = new PlanetaDatosSinFotos();
        dto.setIdPlaneta(planeta.getIdPlaneta());
        dto.setNombre(planeta.getNombre());
        dto.setDiametro(planeta.getDiametro());
        dto.setMasa(planeta.getMasa());
        dto.setCaracteristicas(planeta.getCaracteristicas());
        dto.setComentarios(planeta.getComentarios());
        dto.setMimeType(planeta.getMimeType());
        dto.setFotoLogoOriginal(planeta.getFotoLogoOriginal());
        dto.setFotoLogoThumb(planeta.getFotoLogoThumb());
        dto.setUserid(planeta.getUser().getId());

        return ResponseEntity.ok(new GeneralResponseOk("Datos del planeta obtenidos correctamente", dto));
    }

    @GetMapping("/planetas")
    public GeneralResponseOk obtenerDatosDeVariosPlanetas(@RequestParam List<Long> ids) {
        List<PlanetaDatosSinFotos> planetasDTO = new ArrayList<>();
        for (Long id : ids) {
            Planeta planeta = planetaService.obtenerPlanetaSinFotos(id);
            PlanetaDatosSinFotos dto = new PlanetaDatosSinFotos();
            dto.setIdPlaneta(planeta.getIdPlaneta());
            dto.setNombre(planeta.getNombre());
            dto.setDiametro(planeta.getDiametro());
            dto.setMasa(planeta.getMasa());
            dto.setCaracteristicas(planeta.getCaracteristicas());
            dto.setComentarios(planeta.getComentarios());
            dto.setMimeType(planeta.getMimeType());
            dto.setFotoLogoOriginal(planeta.getFotoLogoOriginal());
            dto.setFotoLogoThumb(planeta.getFotoLogoThumb());
            dto.setUserid(planeta.getUser().getId());
            planetasDTO.add(dto);
        }
        return new GeneralResponseOk("Datos de los planetas obtenidos correctamente", planetasDTO);
    }

}
