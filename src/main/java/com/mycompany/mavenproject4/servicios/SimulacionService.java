package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.mycompany.mavenproject4.repository.SimulacionRepository;

@Service
public class SimulacionService {

    private final SimulacionRepository simulacionRepository;

    public SimulacionService(SimulacionRepository SimulacionRepository) {
        this.simulacionRepository = SimulacionRepository;
    }

    public Simulacion crearSimulacion(Simulacion Simulacion) {
        return simulacionRepository.save(Simulacion);
    }


    public Optional<Simulacion> obtenerPorId(Long id) {
        return simulacionRepository.findById(id);
    }

    public void eliminarSimulacion(Long id) {
        simulacionRepository.deleteById(id);
    }
    
    public List<SimulacionLightDTO> listar() {
        return simulacionRepository.findAllLight();
    }

    public SimulacionHeavyDTO obtenerFoto(Long id) {
        return simulacionRepository.findFotoById(id);
    }
    



}