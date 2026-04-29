package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.FotoCreate;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
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
}

//
//
//
//    // =========================
//    // LIST (PAGINADO)
//    // =========================
//    @GetMapping
//    public ResponseEntity<Page<FotoInfo>> listFotos(Pageable pageable) {
//
//        Page<FotoInfo> fotos = fotoService.listFotos(pageable);
//        return ResponseEntity.ok(fotos);
//    }
//
//    // =========================
//    // GET BY ID
//    // =========================
//    @GetMapping("/{id}")
//    public ResponseEntity<FotoInfo> getFoto(@PathVariable Long id) {
//
//        FotoInfo foto = fotoService.getFotoById(id);
//        return ResponseEntity.ok(foto);
//    }
//
//    // =========================
//    // DELETE
//    // =========================
//    @DeleteMapping("/{id}")
//    public ResponseEntity<GeneralResponseOk> deleteFoto(@PathVariable Long id) {
//
//        GeneralResponseOk response = fotoService.deleteFoto(id);
//        return ResponseEntity.ok(response);
//    }
//}
