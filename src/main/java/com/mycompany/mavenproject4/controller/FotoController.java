package com.mycompany.mavenproject4.controller;

import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.servicios.FotoService;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/abm")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    // 🔥 UPLOAD
    @PostMapping("/fotos/upload")
    public ResponseEntity<?> uploadFoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("clasificacion") String clasificacion,
            @RequestParam("tablaasociada") String tablaasociada,
            @RequestParam("idasociado") Long idasociado
    ) throws IOException {

        System.out.println("Uploading OK");

        // Validación simple de tipo
        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/png") &&
                 !contentType.equals("image/jpeg"))) {

            return ResponseEntity.badRequest()
                    .body("Solo se permiten imágenes PNG o JPG");
        }

        Foto foto = fotoService.guardarFoto(
                file,
                descripcion,
                clasificacion,
                tablaasociada,
                idasociado
        );

        return ResponseEntity.ok(foto);
    }

    // 🔥 TRAER UNA IMAGEN POR ID
    @GetMapping("/fotos/ver/{id}")
    public ResponseEntity<byte[]> verFoto(@PathVariable Long id) {

        Foto foto = fotoService.buscarPorId(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foto.getMimeType()))
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"imagen\"")
                .body(foto.getArchivo());
    }

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
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(fotos);
    }

    // 🔥 DELETE POR ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarFoto(@PathVariable Long id) {
        fotoService.eliminarFoto(id);
        return ResponseEntity.noContent().build();
    }

    // 🔥 PATCH DESCRIPCIÓN
    @PatchMapping("/editdescripcion/{id}")
    public ResponseEntity<?> actualizarDescripcion(
            @PathVariable Long id,
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
@PostMapping("/fotos/upload-and-reset")
public ResponseEntity<?> uploadFotoAndReset(
        @RequestParam("file") MultipartFile file,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("clasificacion") String clasificacion,
        @RequestParam("tablaasociada") String tablaasociada,
        @RequestParam("idasociado") Long idasociado
) throws IOException {

    // Validación simple de tipo
    String contentType = file.getContentType();
    if (contentType == null ||
            (!contentType.equals("image/png") &&
             !contentType.equals("image/jpeg"))) {
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