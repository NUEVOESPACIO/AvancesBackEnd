package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.dto.PracticaRequestDTO;
import com.mycompany.mavenproject4.dto.PracticaResponseDTO;
import com.mycompany.mavenproject4.entidades.Practica;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.PracticaService;
import com.mycompany.mavenproject4.servicios.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/abm")
public class PracticaController {

    private final PracticaService practicaService;
    private final UserService userService;

    public PracticaController(PracticaService practicaService, UserService userService) {
        this.practicaService = practicaService;
        this.userService = userService;
    }

    @PostMapping("/practicas/create")
    public ResponseEntity<PracticaResponseDTO> crear(@RequestBody PracticaRequestDTO data) {

        Practica practica = new Practica();

        // datos básicos
        practica.setNombre(data.getNombre());
        practica.setDescripcion(data.getDescripcion());

        // imagen base64
        if (data.getFotoBase64() != null && !data.getFotoBase64().isEmpty()) {

            byte[] fotoBytes = Base64.getDecoder().decode(data.getFotoBase64());

            practica.setFoto(fotoBytes);
            practica.setMimeType(data.getMimeType());
            practica.setSize((long) fotoBytes.length);
        }

        // buscar usuario
        User user = userService.obtenerPorId(data.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        practica.setUser(user);

        // guardar
        Practica nueva = practicaService.crearPractica(practica);

        // crear response DTO
        PracticaResponseDTO response = new PracticaResponseDTO();
        response.setId(nueva.getIdPractica());response.setStatus(Boolean.TRUE);
        response.setMessage("Practica creada correctamente");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/practicas/listar")
    public List<Practica> obtenerTodas() {
        return practicaService.obtenerTodas();
    }

    @GetMapping("/practicas/obtener/{id}")
    public ResponseEntity<Practica> obtenerPorId(@PathVariable Long id) {

        return practicaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/practicas/borrar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        practicaService.eliminar(id);

        return ResponseEntity.ok("Practica eliminada");
    }
}