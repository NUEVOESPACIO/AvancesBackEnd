package com.mycompany.mavenproject4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mycompany.mavenproject4.entidades.Foto;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    List<Foto> findByTablaasociadaAndIdasociadoAndClasificacion(
        String tablaasociada,
        Long idasociado,
        String clasificacion
    );

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Foto f
        WHERE f.tablaasociada = :tablaasociada
        AND f.idasociado = :idasociado
        AND f.clasificacion = :clasificacion
    """)
    int deleteByTablaasociadaAndIdasociadoAndClasificacion(
        @Param("tablaasociada") String tablaasociada,
        @Param("idasociado") Long idasociado,
        @Param("clasificacion") String clasificacion
    );
}