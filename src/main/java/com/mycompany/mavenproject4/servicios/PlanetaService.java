package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.CreatePlanetaDto;
import com.mycompany.mavenproject4.dto.EditPlanetaDTO;
import com.mycompany.mavenproject4.dto.FotoCreate;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.PlanetaDatosConFotos;
import com.mycompany.mavenproject4.dto.PlanetaDatosSinFotos;
import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.DefaultImageUserNotFoundException;
import com.mycompany.mavenproject4.exception.DiametroRequeridoException;
import com.mycompany.mavenproject4.exception.ErrorEnArchivoLogoPlanetaException;
import com.mycompany.mavenproject4.exception.LogoPlanetaDebeSerPng;
import com.mycompany.mavenproject4.exception.MasaRequeridaException;
import com.mycompany.mavenproject4.exception.NombreRequeridoException;
import com.mycompany.mavenproject4.exception.NombreYaExistenteException;
import com.mycompany.mavenproject4.exception.PlanetaEnSimulacionExistente;
import com.mycompany.mavenproject4.exception.PlanetaNotFoundException;
import com.mycompany.mavenproject4.exception.UserNotFoundException;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.repository.PlanetaSimulacionRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.utils.comprimirPNGtoBytes;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PlanetaService {

    private final PlanetaRepository planetaRepository;
    private final UserRepository userRepository;
    private final FotoService fotoService;
    private final PlanetaSimulacionRepository planetasimulacionrepository;

    public PlanetaService(PlanetaRepository planetaRepository, UserRepository userRepository, FotoService fotoService, PlanetaSimulacionRepository planetasimulacionrepository) {
        this.planetaRepository = planetaRepository;
        this.userRepository = userRepository;
        this.fotoService = fotoService;
        this.planetasimulacionrepository = planetasimulacionrepository;
    }

    public GeneralResponseOk createPlaneta(CreatePlanetaDto request) {

        // 1. Buscar usuario
        User user = userRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new UserNotFoundException());

        Planeta planeta = new Planeta();

        planeta.setUser(user);

        if (request.getNombre() == null || request.getNombre().isEmpty()) {
            throw new NombreRequeridoException();
        }

        if (planetaRepository.existsByNombre(request.getNombre())) {
            throw new NombreYaExistenteException();
        }

        if (request.getDiametro() == null) {
            throw new DiametroRequeridoException();
        }

        if (request.getMasa() == null) {
            throw new MasaRequeridaException();
        }

        // 3. Actualizar los valores solo si son válidos
        planeta.setNombre(request.getNombre());
        planeta.setDiametro(request.getDiametro());
        planeta.setMasa(request.getMasa());

        // 4. Validar y actualizar otros campos como 'caracteristicas', 'comentarios', 'archivo' si es necesario
        if (request.getCaracteristicas() != null) {
            planeta.setCaracteristicas(request.getCaracteristicas());
        }

        if (request.getComentarios() != null) {
            planeta.setComentarios(request.getComentarios());
        }

        byte[] fotoLogoArchivo;
        try {
            fotoLogoArchivo = resolverFotoPNG(request.getArchivo());
        } catch (IOException ex) {
            throw new ErrorEnArchivoLogoPlanetaException();
        }

        planeta.setFotoLogoOriginal(fotoLogoArchivo);
        planeta.setMimeType("image/png");

        try {
            planeta.setFotoLogoThumb(comprimirPNGtoBytes.comprimirPNG(fotoLogoArchivo, 0.8f));
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

    public GeneralResponseOk editarPlaneta(Long id, EditPlanetaDTO request) {
        // 1. Buscar usuario
        Planeta planeta = planetaRepository.findById(id)
                .orElseThrow(() -> new PlanetaNotFoundException());

        // 2. Validar y actualizar diametro y masa (obligatorios)
        if (request.getDiametro() == null) {
            throw new DiametroRequeridoException();  // Excepción personalizada si el diámetro es nulo
        }
        if (request.getMasa() == null) {
            throw new MasaRequeridaException();  // Excepción personalizada si la masa es nula
        }

        // Solo actualizamos si el valor es diferente
        if (!request.getDiametro().equals(planeta.getDiametro())) {
            planeta.setDiametro(request.getDiametro());
        }

        if (!request.getMasa().equals(planeta.getMasa())) {
            planeta.setMasa(request.getMasa());
        }
        planeta.setCaracteristicas(request.getCaracteristicas());
        planeta.setComentarios(request.getComentarios());

        // 5. Validación y actualización del archivo (solo si cambia)
        if (request.getArchivo() != null) {
            if (!isPng(request.getArchivo())) {
                throw new LogoPlanetaDebeSerPng();  // Verificar si el archivo es PNG
            }

            // Si el archivo ha cambiado, actualizamos la imagen
            if (!Arrays.equals(request.getArchivo(), planeta.getFotoLogoOriginal())) {
                planeta.setFotoLogoOriginal(request.getArchivo());
                planeta.setMimeType("image/png");

                // Comprimir la imagen PNG
                try {
                    planeta.setFotoLogoThumb(comprimirPNGtoBytes.comprimirPNG(request.getArchivo(), 0.8f));
                } catch (IOException ex) {
                    throw new ErrorEnArchivoLogoPlanetaException();  // Excepción personalizada si ocurre un error al comprimir la imagen
                }
            }
        }

        // 6. Guardar la entidad actualizada
        planetaRepository.save(planeta);

        return new GeneralResponseOk("Planeta actualizado correctamente", planeta.getIdPlaneta());
    }

    public GeneralResponseOk eliminarPlaneta(Long id) {

        Planeta planeta = planetaRepository.findById(id)
                .orElseThrow(() -> new PlanetaNotFoundException());

        if (planetasimulacionrepository.existsByPlaneta(planeta)) {
            // Si hay alguna simulación asociada, lanzamos una excepción o respondemos con un mensaje.
            throw new PlanetaEnSimulacionExistente();
        }

        // 2. Si no hay simulaciones asociadas, proceder con la eliminación del planeta
        planetaRepository.delete(planeta);

        // 3. Responder con un mensaje de éxito
        return new GeneralResponseOk("Planeta eliminado correctamente", id);
    }

    public byte[] resolverFotoPNG(byte[] fotoOriginal) throws IOException {

        if (!isPng(fotoOriginal)) {
            throw new LogoPlanetaDebeSerPng();
        }

        try {
            if (fotoOriginal != null && fotoOriginal.length > 0) {
                return comprimirPNGtoBytes.comprimirPNG(fotoOriginal, 1.0f);
            }
        } catch (IOException iOException) {
            throw new ErrorEnArchivoLogoPlanetaException();
        }

        try ( InputStream is = getClass().getResourceAsStream("/static/planeta_default.png")) {
            if (is == null) {
                throw new DefaultImageUserNotFoundException();
            }
            byte[] defaultImage = is.readAllBytes();
            return comprimirPNGtoBytes.comprimirPNG(defaultImage, 1.0f);
        }
    }

    public Planeta obtenerPlanetaConFotos(Long id) {
        Planeta planeta = planetaRepository.findById(id)
                .orElseThrow(() -> new PlanetaNotFoundException());
        // Aquí puedes cargar las fotos si están relacionadas correctamente
        return planeta;
    }

    public PlanetaDatosSinFotos obtenerPlanetaSinFotos(Long id) {
        Planeta planeta = planetaRepository.findById(id)
                .orElseThrow(() -> new PlanetaNotFoundException());
        PlanetaDatosSinFotos dto = new PlanetaDatosSinFotos();
        dto.setIdPlaneta(planeta.getIdPlaneta());
        dto.setNombre(planeta.getNombre());
        dto.setDiametro(planeta.getDiametro());
        dto.setMasa(planeta.getMasa());
        dto.setCaracteristicas(planeta.getCaracteristicas());
        dto.setComentarios(planeta.getComentarios());
        dto.setMimeType(planeta.getMimeType());
        dto.setFotoLogoOriginal(planeta.getFotoLogoOriginal());
        dto.setFotoLogoThumb(planeta.getFotoLogoThumb());
        dto.setUserid(planeta.getUser().getId());

        return dto;
    }

    public List<PlanetaDatosSinFotos> obtenerInfoDeVariosPlanetas(@RequestParam List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();  // Retorna una lista vacía si los ids son nulos o vacíos
        }

        return ids.stream()
                .map(this::obtenerPlanetaSinFotos) // Mapea cada id al objeto PlanetaDatosSinFotos
                .collect(Collectors.toList());  // Recoge los resultados en una lista
    }
    
        public Page<PlanetaDatosConFotos> obtenerPlanetasPaginados(Pageable pageable) {
        // Usamos el repositorio para obtener planetas con fotos paginados
        Page<Planeta> planetasPage = planetaRepository.findAll(pageable);

        // Convertimos los planetas a PlanetaDatosConFotos (un DTO con fotos incluidas)
        return planetasPage.map(planeta -> {
            PlanetaDatosConFotos dto = new PlanetaDatosConFotos();
            dto.setIdPlaneta(planeta.getIdPlaneta()); // Asignamos el ID del planeta
            dto.setNombre(planeta.getNombre()); // Asignamos el nombre del planeta
            dto.setDiametro(planeta.getDiametro()); // Asignamos el diámetro del planeta
            dto.setMasa(planeta.getMasa()); // Asignamos la masa del planeta
            dto.setCaracteristicas(planeta.getCaracteristicas()); // Asignamos las características
            dto.setComentarios(planeta.getComentarios()); // Asignamos los comentarios
            dto.setMimeType(planeta.getMimeType()); // Asignamos el MIME type de la foto
            dto.setFotoLogoOriginal(planeta.getFotoLogoOriginal()); // Asignamos la foto original
            dto.setFotoLogoThumb(planeta.getFotoLogoThumb()); // Asignamos la foto thumbnail
            dto.setUserid(planeta.getUser().getId()); // Asignamos el ID del usuario
            dto.setFotos(planeta.getFotos()); // Asignam
            return dto;
        });
    }

}
