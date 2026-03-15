package com.mycompany.mavenproject4.controller;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.dto.SimulacionResponseDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.SimulacionService;
import com.mycompany.mavenproject4.servicios.UserService;
import java.util.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abm")
public class SimulacionController {

    private final SimulacionService simulacionService;
    private final UserService userService;

    public SimulacionController(SimulacionService simulacionService, UserService userService) {
        this.simulacionService = simulacionService;
        this.userService = userService;
    }

    @PostMapping("/simulaciones/create")
    public ResponseEntity<SimulacionResponseDTO> crear(@RequestBody SimulacionRequestDTO data) {

        Simulacion simulacion = new Simulacion();

        // datos básicos
        simulacion.setNombre(data.getNombre());
        simulacion.setDescripcion(data.getDescripcion());

        // imagen base64
        if (data.getFotoBase64() != null && !data.getFotoBase64().isEmpty()) {

            byte[] fotoBytes = Base64.getDecoder().decode(data.getFotoBase64());

            simulacion.setFoto(fotoBytes);
            simulacion.setMimeType(data.getMimeType());
            simulacion.setSize((long) fotoBytes.length);
        }

        // buscar usuario
        User user = userService.obtenerPorId(data.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        simulacion.setUser(user);

        // guardar
        Simulacion nueva = simulacionService.crearSimulacion(simulacion);

        // crear response DTO
        SimulacionResponseDTO response = new SimulacionResponseDTO();
        response.setId(nueva.getIdSimulacion());response.setStatus(Boolean.TRUE);
        response.setMessage("Practica creada correctamente");

        return ResponseEntity.ok(response);
    }
}