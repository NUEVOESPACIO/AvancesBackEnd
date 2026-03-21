package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.dto.SimulacionResponseDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.SimulacionService;
import com.mycompany.mavenproject4.servicios.UserService;
import static com.mycompany.mavenproject4.utils.BeanUtilsHelper.getNullPropertyNames;
import static com.mycompany.mavenproject4.utils.comprimirJpegToBytes.comprimirJpeg;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<SimulacionResponseDTO> crear(@RequestBody SimulacionRequestDTO data)  {

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
            byte[] imagenPreview = null;
            try {
                imagenPreview = comprimirJpeg(fotoBytes, 0.7f);
            } catch (IOException ex) {
                Logger.getLogger(SimulacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            simulacion.setFotoPreview(imagenPreview);
        }

        // buscar usuario
        User user = userService.obtenerPorId(data.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        simulacion.setUser(user);

        // guardar
        Simulacion nueva = simulacionService.crearSimulacion(simulacion);

        // crear response DTO
        SimulacionResponseDTO response = new SimulacionResponseDTO();
        response.setId(nueva.getIdSimulacion());
        response.setStatus(Boolean.TRUE);
        response.setMessage("Practica creada correctamente");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/simulaciones/eliminar")
    public ResponseEntity<?> eliminar(@RequestParam Long id) {

        simulacionService.eliminarSimulacion(id);

        return ResponseEntity.ok("Simulacion eliminada correctamente");
    }

    @PatchMapping("/simulaciones/editar")
    public ResponseEntity<SimulacionResponseDTO> editarParcial(
            @RequestParam Long id,
            @RequestBody SimulacionRequestDTO data) throws IOException {

        Simulacion simulacion = simulacionService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Simulacion no encontrada"));

        // copiar solo campos no null
        BeanUtils.copyProperties(data, simulacion, getNullPropertyNames(data));

        // actualizar foto si viene base64
        if (data.getFotoBase64() != null) {

            byte[] fotoBytes = Base64.getDecoder().decode(data.getFotoBase64());

            simulacion.setFoto(fotoBytes);
            simulacion.setMimeType(data.getMimeType());
            simulacion.setSize((long) fotoBytes.length);
            byte[] imagenPreview = comprimirJpeg(fotoBytes, 0.7f);
            simulacion.setFotoPreview(imagenPreview);
        }

        // actualizar usuario
        if (data.getUserId() != null) {

            User user = userService.obtenerPorId(data.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            simulacion.setUser(user);
        }

        Simulacion actualizada = simulacionService.crearSimulacion(simulacion);

        SimulacionResponseDTO response = new SimulacionResponseDTO();
        response.setId(actualizada.getIdSimulacion());
        response.setStatus(true);
        response.setMessage("Simulacion actualizada correctamente");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/simulaciones/listar")
    public List<SimulacionLightDTO> listar() {
        return simulacionService.listar();
    }

    @GetMapping("/simulaciones/getfoto")
    public SimulacionHeavyDTO foto(@RequestParam Long id) {
        return simulacionService.obtenerFoto(id);
    }

}
