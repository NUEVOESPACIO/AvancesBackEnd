package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SimulacionRepository extends JpaRepository<Simulacion, Long> {
    
    
@Query("""
        SELECT new com.mycompany.mavenproject4.dto.SimulacionLightDTO(
            s.idSimulacion,
            s.nombre,
            s.descripcion,
            s.mimeType,
            s.size,
            s.fotoPreview
        )
        FROM Simulacion s
    """)
    List<SimulacionLightDTO> findAllLight();


    @Query("""
        SELECT new com.mycompany.mavenproject4.dto.SimulacionHeavyDTO(
            s.idSimulacion,
            s.foto,
            s.mimeType
        )
        FROM Simulacion s
        WHERE s.idSimulacion = :id
    """)
    SimulacionHeavyDTO findFotoById(Long id);
    
}
