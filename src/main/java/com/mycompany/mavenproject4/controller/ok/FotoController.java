package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.FotoCreate;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.servicios.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @PostMapping("/createfoto")
    public ResponseEntity<GeneralResponseOk> createFoto(@RequestBody FotoCreate request) {

        GeneralResponseOk response = fotoService.createFoto(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseOk> deleteFoto(@PathVariable Long id) {

        GeneralResponseOk response = fotoService.deleteFoto(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/descripcion")
    public ResponseEntity<GeneralResponseOk> actualizarDescripcion(
            @PathVariable Long id,
            @RequestBody String nuevaDescripcion) {

        GeneralResponseOk response = fotoService.updateDescripcionFoto(id, nuevaDescripcion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/archivo")
    public ResponseEntity<byte[]> obtenerArchivo(@PathVariable Long id) {

        Foto foto = fotoService.obtenerArchivoFoto(id);

        return ResponseEntity.ok()
                .header("Content-Type", foto.getMimeType())
                .header("Content-Disposition", "inline; filename=\"" + foto.getNombreArchivo() + "\"")
                .body(foto.getArchivo());
    }
}
