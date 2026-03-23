package com.mycompany.mavenproject4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mycompany.mavenproject4.entidades.Foto;


@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {




}