package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.FotoCreate;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.exception.ErrorEnArchivoFotoPlanetaException;
import com.mycompany.mavenproject4.exception.FotoNoEsJpgException;
import com.mycompany.mavenproject4.exception.PlanetaNotFoundException;
import com.mycompany.mavenproject4.repository.FotoRepository;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.utils.comprimirJpegToBytes;
import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;
    private PlanetaRepository planetaRepository;

    // =========================
    // CREATE
    // =========================
    public GeneralResponseOk createFoto(FotoCreate request) {

        Foto foto = new Foto();
        foto.setArchivo(request.getArchivo());
        foto.setNombreArchivo(request.getNombreArchivo());
        foto.setDescripcion(request.getDescripcion());

        if (!isJpeg(request.getArchivo())) {
            throw new FotoNoEsJpgException();
        }
        foto.setMimeType("image/jpeg");
        foto.setSize(request.getArchivo().length);
        try {
            foto.setFotoPreview(comprimirJpegToBytes.comprimirJpeg(request.getArchivo(), 0.7f));
        } catch (IOException ex) {

            throw new ErrorEnArchivoFotoPlanetaException();

        }

        Planeta planeta = planetaRepository.findById(request.getIdplaneta())
                .orElseThrow(() -> new PlanetaNotFoundException());

        foto.setPlaneta(planeta);

        fotoRepository.save(foto);

        return new GeneralResponseOk("Foto creada correctamente", foto.getIdFoto());
    }

//
//    // =========================
//    // LIST (PAGINADO)
//    // =========================
//    public Page<FotoInfo> listFotos(Pageable pageable) {
//
//        return fotoRepository.findAll(pageable)
//                .filter(f -> f.getDeletedAt() == null) // si querés soft delete
//                .map(this::mapToFotoInfo);
//    }
//
//    // =========================
//    // GET BY ID
//    // =========================
//    public FotoInfo getFotoById(Long id) {
//
//        Foto foto = fotoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));
//
//        if (foto.getDeletedAt() != null) {
//            throw new RuntimeException("La foto está eliminada");
//        }
//
//        return mapToFotoInfo(foto);
//    }
//
//    // =========================
//    // DELETE (SOFT DELETE)
//    // =========================
//    public GeneralResponseOk deleteFoto(Long id) {
//
//        Foto foto = fotoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));
//
//        foto.setDeletedAt(LocalDateTime.now());
//        fotoRepository.save(foto);
//
//        return new GeneralResponseOk("Foto eliminada correctamente");
//    }
//
//    // =========================
//    // MAPPER
//    // =========================
//    private FotoInfo mapToFotoInfo(Foto foto) {
//
//        FotoInfo dto = new FotoInfo();
//        dto.setId(foto.getId());
//        dto.setUrl(foto.getUrl());
//        dto.setTitle(foto.getTitle());
//        dto.setCreatedAt(foto.getCreatedAt());
//
//        return dto;
//    }
    public boolean isJpeg(byte[] fotoBytes) {

        Tika tika = new Tika();
        String mimeType = tika.detect(fotoBytes);

        return "image/jpeg".equals(mimeType);
    }

}
