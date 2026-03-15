
package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Simulacion;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SimulacionRepositoryTest {

    @Autowired
    private SimulacionRepository repository;

    @Test
    void guardarSimulacion() {

        Simulacion sim = new Simulacion();
        sim.setNombre("test");

        Simulacion guardada = repository.save(sim);

        assertNotNull(guardada.getIdSimulacion());
    }
}