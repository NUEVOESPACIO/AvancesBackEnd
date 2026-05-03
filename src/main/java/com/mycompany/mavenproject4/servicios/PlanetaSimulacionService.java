package com.mycompany.mavenproject4.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.PlanetaSimulacionRequest;
import com.mycompany.mavenproject4.dto.PlanetaSimulacionResponse;
import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.PlanetaSimulacion;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.repository.PlanetaSimulacionRepository;
import com.mycompany.mavenproject4.repository.PlanetaRepository;
import com.mycompany.mavenproject4.repository.SimulacionRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanetaSimulacionService {

    @Autowired
    private PlanetaSimulacionRepository planetaSimulacionRepository;

    @Autowired
    private PlanetaRepository planetaRepository;

    @Autowired
    private SimulacionRepository simulacionRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public GeneralResponseOk create(PlanetaSimulacionRequest request) {

        // =========================
        // VALIDACIONES
        // =========================

        if (request.getIdPlaneta() == null) {
            throw new RuntimeException("idPlaneta es requerido");
        }

        if (request.getIdSimulacion() == null) {
            throw new RuntimeException("idSimulacion es requerido");
        }

        if (request.getNombrePlanetaSimulacion() == null || request.getNombrePlanetaSimulacion().isEmpty()) {
            throw new RuntimeException("nombrePlanetaSimulacion es requerido");
        }

        if (request.getX() == null || request.getY() == null ||
            request.getV() == null || request.getAngulo() == null) {
            throw new RuntimeException("x, y, v y angulo no pueden ser null");
        }

        // =========================
        // EXISTENCIAS
        // =========================

        Planeta planeta = planetaRepository.findById(request.getIdPlaneta())
                .orElseThrow(() -> new RuntimeException("Planeta no existe"));

        Simulacion simulacion = simulacionRepository.findById(request.getIdSimulacion())
                .orElseThrow(() -> new RuntimeException("Simulacion no existe"));

        // =========================
        // VALIDAR NOMBRE ÚNICO POR SIMULACIÓN
        // =========================

        boolean existeNombre = planetaSimulacionRepository
                .existsBySimulacionAndNombrePlanetaSimulacion(simulacion, request.getNombrePlanetaSimulacion());

        if (existeNombre) {
            throw new RuntimeException("Ya existe un planeta con ese nombre en la simulación");
        }

        // =========================
        // VALIDAR JSON (payload)
        // =========================

        if (request.getPayload() != null) {
            try {
                objectMapper.readTree(request.getPayload());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("El payload no es un JSON válido");
            }
        }

        // =========================
        // CREAR ENTIDAD
        // =========================

        PlanetaSimulacion ps = new PlanetaSimulacion();

        ps.setPlaneta(planeta);
        ps.setSimulacion(simulacion);
        ps.setNombrePlanetaSimulacion(request.getNombrePlanetaSimulacion());
        ps.setX(request.getX());
        ps.setY(request.getY());
        ps.setV(request.getV());
        ps.setAngulo(request.getAngulo());
        ps.setPayload(request.getPayload());

        planetaSimulacionRepository.save(ps);

        return new GeneralResponseOk("Planeta agregado a simulacion", ps.getId());
    }
    
    @Transactional
public GeneralResponseOk update(Long id, PlanetaSimulacionRequest request) {

    // =========================
    // BUSCAR EXISTENTE
    // =========================
    PlanetaSimulacion ps = planetaSimulacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PlanetaSimulacion no existe"));

    // =========================
    // VALIDACIONES BÁSICAS
    // =========================

    if (request.getNombrePlanetaSimulacion() == null || request.getNombrePlanetaSimulacion().isEmpty()) {
        throw new RuntimeException("nombrePlanetaSimulacion es requerido");
    }

    if (request.getX() == null || request.getY() == null ||
        request.getV() == null || request.getAngulo() == null) {
        throw new RuntimeException("x, y, v y angulo no pueden ser null");
    }

    // =========================
    // VALIDAR CAMBIO DE PLANETA
    // =========================

    if (request.getIdPlaneta() != null) {
        Planeta planeta = planetaRepository.findById(request.getIdPlaneta())
                .orElseThrow(() -> new RuntimeException("Planeta no existe"));

        ps.setPlaneta(planeta);
    }

    // =========================
    // ⚠️ NO permitir cambiar simulacion
    // =========================
    if (request.getIdSimulacion() != null &&
        !request.getIdSimulacion().equals(ps.getSimulacion().getIdSimulacion())) {
        throw new RuntimeException("No se puede cambiar la simulacion");
    }

    // =========================
    // VALIDAR NOMBRE ÚNICO
    // =========================

    boolean existeNombre = planetaSimulacionRepository
            .existsBySimulacionAndNombrePlanetaSimulacion(
                    ps.getSimulacion(),
                    request.getNombrePlanetaSimulacion()
            );

    // ⚠️ IMPORTANTE: evitar falso positivo contra sí mismo
    if (existeNombre &&
        !ps.getNombrePlanetaSimulacion().equals(request.getNombrePlanetaSimulacion())) {
        throw new RuntimeException("Ya existe un planeta con ese nombre en la simulación");
    }

    // =========================
    // VALIDAR JSON
    // =========================

    if (request.getPayload() != null) {
        try {
            objectMapper.readTree(request.getPayload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("El payload no es un JSON válido");
        }
    }

    // =========================
    // UPDATE
    // =========================

    ps.setNombrePlanetaSimulacion(request.getNombrePlanetaSimulacion());
    ps.setX(request.getX());
    ps.setY(request.getY());
    ps.setV(request.getV());
    ps.setAngulo(request.getAngulo());
    ps.setPayload(request.getPayload()); // puede ser null ✔️

    planetaSimulacionRepository.save(ps);

    return new GeneralResponseOk("PlanetaSimulacion actualizado", ps.getId());
}
@Transactional
public GeneralResponseOk delete(Long id) {

    PlanetaSimulacion ps = planetaSimulacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PlanetaSimulacion no existe"));

    planetaSimulacionRepository.delete(ps);

    return new GeneralResponseOk("PlanetaSimulacion eliminada correctamente", id);
}

public List<PlanetaSimulacionResponse> getBySimulacion(Long idSimulacion) {

    List<PlanetaSimulacion> lista = planetaSimulacionRepository
            .findBySimulacionWithPlaneta(idSimulacion);

    if (lista.isEmpty()) {
        throw new RuntimeException("No hay planetas para esta simulacion");
    }

    return lista.stream()
            .map(ps -> new PlanetaSimulacionResponse(
                    ps.getNombrePlanetaSimulacion(),
                    ps.getX(),
                    ps.getY(),
                    ps.getV(),
                    ps.getAngulo(),
                    ps.getPayload(),
                    ps.getPlaneta().getNombre(),     // 👈 planeta
                    ps.getPlaneta().getDiametro(),
                    ps.getPlaneta().getMasa(),
                    ps.getPlaneta().getFotoLogoOriginal(),
                    ps.getPlaneta().getMimeType()
            ))
            .toList();
}
}