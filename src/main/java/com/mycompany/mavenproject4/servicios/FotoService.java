package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.FotoCreate;
import com.mycompany.mavenproject4.dto.FotoPreviewDto;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.exception.ErrorEnArchivoFotoPlanetaException;
import com.mycompany.mavenproject4.exception.FotoNoEncontradaException;
import com.mycompany.mavenproject4.exception.FotoNoEsJpgException;
import com.mycompany.mavenproject4.exception.PlanetaNotFoundException;
import com.mycompany.mavenproject4.repository.FotoRepository;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.utils.comprimirJpegToBytes;
import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        return new GeneralResponseOk("Foto creada correctamente", foto);
    }

    public GeneralResponseOk deleteFoto(Long id) {

        if (!fotoRepository.existsById(id)) {
            throw new FotoNoEncontradaException();
        }

        fotoRepository.deleteById(id);

        return new GeneralResponseOk("Foto eliminada correctamente", id);
    }

    public boolean isJpeg(byte[] fotoBytes) {

        Tika tika = new Tika();
        String mimeType = tika.detect(fotoBytes);

        return "image/jpeg".equals(mimeType);
    }

    public GeneralResponseOk updateDescripcionFoto(Long id, String nuevaDescripcion) {

        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new FotoNoEncontradaException());

        foto.setDescripcion(nuevaDescripcion);

        fotoRepository.save(foto);

        return new GeneralResponseOk("Descripción actualizada correctamente", id);
    }
    
    public Foto obtenerArchivoFoto(Long id) {
    return fotoRepository.findById(id)
            .orElseThrow(() -> new FotoNoEncontradaException());
}
    

public Page<FotoPreviewDto> listarFotos(Pageable pageable) {

    Page<Foto> fotos = fotoRepository.findAll(pageable);

    return fotos.map(f -> new FotoPreviewDto(
            f.getIdFoto(),
            f.getNombreArchivo(),
            f.getDescripcion(),
            f.getFotoPreview(),
            f.getPlaneta().getIdPlaneta()
    ));
}

}
