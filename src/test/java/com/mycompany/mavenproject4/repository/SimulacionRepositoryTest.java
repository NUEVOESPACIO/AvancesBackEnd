package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.entidades.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SimulacionRepositoryTest {

    @Autowired
    private SimulacionRepository simulacionRepository;

    @Autowired
    private EntityManager em;

    // =========================
    // HELPER: crear usuario válido
    // =========================
    private User crearUsuario() {
        User user = new User();

        // ⚠️ IMPORTANTE:
        // completá acá los campos obligatorios de tu entidad User
        // Ejemplo:
        // user.setUsername("test");
        // user.setPassword("1234");

        em.persist(user);
        return user;
    }

    // =========================
    // TEST: findAllLight
    // =========================
    @Test
    @DisplayName("findAllLight debe devolver DTOs con datos correctos")
    void testFindAllLight() {

        // GIVEN
        User user = crearUsuario();

        Simulacion sim = new Simulacion();
        sim.setNombre("Sim 1");
        sim.setDescripcion("Desc 1");
        sim.setMimeType("image/png");
        sim.setSize(200L);
        sim.setUser(user);

        simulacionRepository.save(sim);

        // WHEN
        List<SimulacionLightDTO> result = simulacionRepository.findAllLight();

        // THEN
        assertThat(result).hasSize(1);

        SimulacionLightDTO dto = result.get(0);

        assertThat(dto.getIdSimulacion()).isNotNull();
        assertThat(dto.getNombre()).isEqualTo("Sim 1");
        assertThat(dto.getDescripcion()).isEqualTo("Desc 1");
        assertThat(dto.getMimeType()).isEqualTo("image/png");
        assertThat(dto.getSizepreview()).isEqualTo(200L);
    }

    // =========================
    // TEST: findFotoById
    // =========================
    @Test
    @DisplayName("findFotoById debe devolver DTO con foto")
    void testFindFotoById() {

        // GIVEN
        User user = crearUsuario();

        byte[] foto = new byte[]{10, 20, 30};

        Simulacion sim = new Simulacion();
        sim.setNombre("Sim Foto");
        sim.setMimeType("image/jpeg");
        sim.setFoto(foto);
        sim.setUser(user);

        Simulacion saved = simulacionRepository.save(sim);

        // WHEN
        SimulacionHeavyDTO dto =
                simulacionRepository.findFotoById(saved.getIdSimulacion());

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getIdSimulacion()).isEqualTo(saved.getIdSimulacion());
        assertThat(dto.getMimeType()).isEqualTo("image/jpeg");
        assertThat(dto.getFoto()).isEqualTo(foto);
    }

    // =========================
    // TEST: ID inexistente
    // =========================
    @Test
    @DisplayName("findFotoById debe devolver null si no existe")
    void testFindFotoById_NotFound() {

        SimulacionHeavyDTO dto = simulacionRepository.findFotoById(999L);

        assertThat(dto).isNull();
    }

    // =========================
    // TEST: múltiples registros
    // =========================
    @Test
    @DisplayName("findAllLight debe devolver múltiples simulaciones")
    void testFindAllLight_Multiple() {

        User user = crearUsuario();

        Simulacion sim1 = new Simulacion();
        sim1.setNombre("Sim 1");
        sim1.setDescripcion("Desc 1");
        sim1.setMimeType("image/png");
        sim1.setSize(100L);
        sim1.setUser(user);

        Simulacion sim2 = new Simulacion();
        sim2.setNombre("Sim 2");
        sim2.setDescripcion("Desc 2");
        sim2.setMimeType("image/jpeg");
        sim2.setSize(300L);
        sim2.setUser(user);

        simulacionRepository.save(sim1);
        simulacionRepository.save(sim2);

        // WHEN
        List<SimulacionLightDTO> result = simulacionRepository.findAllLight();

        // THEN
        assertThat(result).hasSize(2);
    }
}