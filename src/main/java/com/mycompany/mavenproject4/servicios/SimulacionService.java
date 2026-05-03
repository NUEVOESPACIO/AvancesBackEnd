package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.DefaultImageUserNotFoundException;
import com.mycompany.mavenproject4.exception.ErrorEnFotoSimulacion;
import com.mycompany.mavenproject4.exception.FotoNoEsJpgException;
import com.mycompany.mavenproject4.exception.NombreRequeridoEnSimulacionException;
import com.mycompany.mavenproject4.exception.UserNotFoundException;
import com.mycompany.mavenproject4.repository.PlanetaSimulacionRepository;
import com.mycompany.mavenproject4.repository.SimulacionRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.utils.comprimirJpegToBytes;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulacionService {

    @Autowired
    private SimulacionRepository simulacionRepository;

    @Autowired
    private UserRepository userRepository;  
    
    @Autowired
    private PlanetaSimulacionRepository planetaSimulacionRepository;

    // ============================
    // Crear Simulación
    // ============================
    public GeneralResponseOk createSimulacion(SimulacionRequestDTO requestDTO) {

        if (requestDTO.getNombre() == null || requestDTO.getNombre().isEmpty()) {
            throw new NombreRequeridoEnSimulacionException();
        }

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        Simulacion simulacion = new Simulacion();

        byte[] fotoSimulacionHeavy = null;
        try {
            fotoSimulacionHeavy = resolverFoto(requestDTO.getFotoHeavy());
        } catch (IOException e) {
            throw new ErrorEnFotoSimulacion();
        }

        simulacion.setUser(user);
        simulacion.setDescripcion(requestDTO.getDescripcion());
        simulacion.setNombre(requestDTO.getNombre());
        simulacion.setFoto(requestDTO.getFotoHeavy());
        simulacion.setMimeType("image/jpeg");
        simulacion.setSize((long) requestDTO.getFotoHeavy().length);
        simulacion.setFotoPreview(fotoSimulacionHeavy);

        simulacionRepository.save(simulacion);

        return new GeneralResponseOk("Simulacion creada correctamente",simulacion.getIdSimulacion());

        

    }

    public byte[] resolverFoto(byte[] fotoOriginal) throws IOException {

        if (!isJpg(fotoOriginal)) {
            throw new FotoNoEsJpgException();
        }

        if (fotoOriginal != null && fotoOriginal.length > 0) {
            return comprimirJpegToBytes.comprimirJpeg(fotoOriginal, 0.7f);
        }

        try ( InputStream is = getClass().getResourceAsStream("/static/simulacion-default.jpg")) {
            if (is == null) {
                throw new DefaultImageUserNotFoundException();
            }
            byte[] defaultImage = is.readAllBytes();
            return comprimirJpegToBytes.comprimirJpeg(defaultImage, 0.7f);
        }
    }

    public boolean isJpg(byte[] archivo) {
        Tika tika = new Tika();
        String mimeType = tika.detect(archivo);
        return "image/jpeg".equals(mimeType);
    }
    
    public GeneralResponseOk editSimulacion(Long idSimulacion, SimulacionRequestDTO requestDTO) {

    Simulacion simulacion = simulacionRepository.findById(idSimulacion)
            .orElseThrow(() -> new RuntimeException("Simulacion no encontrada"));

    if (requestDTO.getNombre() == null || requestDTO.getNombre().isEmpty()) {
        throw new NombreRequeridoEnSimulacionException();
    }

    User user = userRepository.findById(requestDTO.getUserId())
            .orElseThrow(UserNotFoundException::new);

    simulacion.setNombre(requestDTO.getNombre());
    simulacion.setDescripcion(requestDTO.getDescripcion());   

    // FOTO (opcional actualizar)
    if (requestDTO.getFotoHeavy() != null && requestDTO.getFotoHeavy().length > 0) {
        try {
            byte[] preview = resolverFoto(requestDTO.getFotoHeavy());

            simulacion.setFoto(requestDTO.getFotoHeavy());
            simulacion.setFotoPreview(preview);
            simulacion.setMimeType("image/jpeg");
            simulacion.setSize((long) requestDTO.getFotoHeavy().length);

        } catch (IOException e) {
            throw new ErrorEnFotoSimulacion();
        }
    }

    simulacionRepository.save(simulacion);

    return new GeneralResponseOk("Simulacion editada correctamente", simulacion.getIdSimulacion());
}
    

public Page<SimulacionLightDTO> listSimulaciones(Pageable pageable) {

    return simulacionRepository.findAll(pageable)
            .map(sim -> new SimulacionLightDTO(
                    sim.getIdSimulacion(),
                    sim.getNombre(),
                    sim.getDescripcion(),
                    sim.getMimeType(),
                    sim.getFotoPreview()
            ));
}

public SimulacionHeavyDTO getSimulacionById(Long idSimulacion) {

    Simulacion sim = simulacionRepository.findById(idSimulacion)
            .orElseThrow(() -> new RuntimeException("Simulacion no encontrada"));

    return new SimulacionHeavyDTO(
            sim.getIdSimulacion(),
            sim.getFoto(),
            sim.getMimeType(),
            sim.getSize()
    );
}

@Transactional
public GeneralResponseOk deleteSimulacion(Long idSimulacion) {

    Simulacion simulacion = simulacionRepository.findById(idSimulacion)
            .orElseThrow(() -> new RuntimeException("Simulacion no encontrada"));

      // 🔥 Después la simulación
    simulacionRepository.delete(simulacion);

    return new GeneralResponseOk("Simulacion eliminada correctamente", idSimulacion);
}

}
