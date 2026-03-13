package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.entidades.Practica;
import com.mycompany.mavenproject4.repository.PracticaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticaService {

    private final PracticaRepository practicaRepository;

    public PracticaService(PracticaRepository practicaRepository) {
        this.practicaRepository = practicaRepository;
    }

    public Practica crearPractica(Practica practica) {
        return practicaRepository.save(practica);
    }

    public List<Practica> obtenerTodas() {
        return practicaRepository.findAll();
    }

    public Optional<Practica> obtenerPorId(Long id) {
        return practicaRepository.findById(id);
    }

    public void eliminar(Long id) {
        practicaRepository.deleteById(id);
    }

}