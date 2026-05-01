package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.CreatePlanetaDto;
import com.mycompany.mavenproject4.dto.FotoCreate;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.ErrorEnArchivoLogoPlanetaException;
import com.mycompany.mavenproject4.exception.LogoPlanetaDebeSerPng;
import com.mycompany.mavenproject4.exception.UserNotFoundException;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.utils.comprimirJpegToBytes;
import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
public class PlanetaService {

    private final PlanetaRepository planetaRepository;
    private final UserRepository userRepository;
    private final FotoService fotoService;

    public PlanetaService(PlanetaRepository planetaRepository,
            UserRepository userRepository,
            FotoService fotoService) {
        this.planetaRepository = planetaRepository;
        this.userRepository = userRepository;
        this.fotoService = fotoService;
    }

    public GeneralResponseOk createPlaneta(CreatePlanetaDto request) {

        // 1. Buscar usuario
        User user = userRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new UserNotFoundException());

        // 2. Crear planeta
        Planeta planeta = new Planeta();
        planeta.setNombre(request.getNombre());
        planeta.setDiametro(request.getDiametro());
        planeta.setMasa(request.getMasa());
        planeta.setCaracteristicas(request.getCaracteristicas());
        planeta.setComentarios(request.getComentarios());
        planeta.setUser(user);

        if (!isPng(request.getArchivo())) {
            throw new LogoPlanetaDebeSerPng();
        }
        planeta.setFotoLogoOriginal(request.getArchivo());
        planeta.setMimeType("image/png");

        try {
            planeta.setFotoLogoThumb(comprimirJpegToBytes.comprimirJpeg(request.getArchivo(), 0.7f));
        } catch (IOException ex) {
            throw new ErrorEnArchivoLogoPlanetaException();
        }

        // 3. Procesar fotos (si vienen)
        if (request.getFotos() != null) {

            for (FotoCreate dto : request.getFotos()) {

                // 🔥 reutilizás tu lógica de FotoService
                GeneralResponseOk resultado = fotoService.createFoto(dto);

                // importante usar helper
                planeta.addFoto((Foto) resultado.getData());
            }
        }

        // 4. Guardar TODO junto
        planetaRepository.save(planeta);

        return new GeneralResponseOk("Planeta creado correctamente", planeta.getIdPlaneta());
    }

    public boolean isPng(byte[] archivo) {
        Tika tika = new Tika();
        String mimeType = tika.detect(archivo);
        return "image/png".equals(mimeType);
    }
}
