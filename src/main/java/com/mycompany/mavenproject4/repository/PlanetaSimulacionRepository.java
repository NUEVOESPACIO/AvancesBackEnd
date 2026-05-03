package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.PlanetaSimulacion;
import com.mycompany.mavenproject4.entidades.Simulacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface PlanetaSimulacionRepository extends JpaRepository<PlanetaSimulacion, Long> {

    public boolean existsByPlaneta(Planeta planeta);

    void deleteBySimulacion(Simulacion simulacion);

    public boolean existsBySimulacionAndNombrePlanetaSimulacion(Simulacion simulacion, String nombrePlanetaSimulacion);

    @Query("""
    SELECT ps FROM PlanetaSimulacion ps
    JOIN FETCH ps.planeta p
    WHERE ps.simulacion.idSimulacion = :idSimulacion
""")
    List<PlanetaSimulacion> findBySimulacionWithPlaneta(Long idSimulacion);

}
