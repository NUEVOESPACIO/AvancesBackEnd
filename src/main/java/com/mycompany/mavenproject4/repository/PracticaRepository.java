package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Practica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticaRepository extends JpaRepository<Practica, Long> {

}
