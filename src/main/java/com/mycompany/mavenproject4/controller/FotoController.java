package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.servicios.FotoService;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/abm")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    // 🔥 BUSCAR POR FILTRO
    @GetMapping("/fotos/buscar")
    public ResponseEntity<?> buscarFotos(
            @RequestParam String tablaasociada,
            @RequestParam Long idasociado,
            @RequestParam String clasificacion
    ) {

        List<Foto> fotos = fotoService.buscarFotos(
                tablaasociada,
                idasociado,
                clasificacion
        );

        return ResponseEntity.ok()
                //    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .cacheControl(CacheControl.noCache())
                .body(fotos);
    }

    // 🔥 DELETE POR ID
    @DeleteMapping("/fotos/delete/{id}")
    public ResponseEntity<?> eliminarFoto(@PathVariable Long id) {
        fotoService.eliminarFoto(id);
        return ResponseEntity.noContent().build();
    }

    // 🔥 PATCH DESCRIPCIÓN
    @PatchMapping("/fotos/editdescripcion")
    public ResponseEntity<?> actualizarDescripcion(
            @RequestParam Long id,
            @RequestParam String descripcion
    ) {
        Foto fotoActualizada = fotoService.actualizarDescripcion(id, descripcion);
        return ResponseEntity.ok(fotoActualizada);
    }

    // 🔥 GET TODAS LAS FOTOS
    @GetMapping("/fotos/buscartodas")
    public ResponseEntity<?> buscarFotosTodas() {
        List<Foto> fotos = fotoService.obtenerTodasLasFotos();

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(fotos);
    }

    // 🔥 NUEVO ENDPOINT: Upload + Reset (Reemplazar foto)
    @PostMapping("/fotos/upload")
    public ResponseEntity<?> uploadFotoAndReset(
            @RequestParam("file") MultipartFile file,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("clasificacion") String clasificacion,
            @RequestParam("tablaasociada") String tablaasociada,
            @RequestParam("idasociado") Long idasociado
    ) throws IOException {

        // Validación simple de tipo
        String contentType = file.getContentType();
        if (contentType == null
                || (!contentType.equals("image/png")
                && !contentType.equals("image/jpeg"))) {
            return ResponseEntity.badRequest()
                    .body("Solo se permiten imágenes PNG o JPG");
        }

        // Llamada al servicio reemplazarFoto (delete + upload en una sola transacción)
        Foto foto = fotoService.reemplazarFoto(
                file,
                descripcion,
                clasificacion,
                tablaasociada,
                idasociado
        );

        return ResponseEntity.ok(foto);
    }

}
