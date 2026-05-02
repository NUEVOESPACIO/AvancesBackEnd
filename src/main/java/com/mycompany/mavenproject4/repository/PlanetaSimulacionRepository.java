
package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Planeta;
import com.mycompany.mavenproject4.entidades.PlanetaSimulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanetaSimulacionRepository extends JpaRepository<PlanetaSimulacion, Long> {

    public boolean existsByPlaneta(Planeta planeta);


    
}