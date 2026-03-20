package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionLightDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.repository.SimulacionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SimulacionServiceTest {

    @Mock
    private SimulacionRepository simulacionRepository;

    @InjectMocks
    private SimulacionService simulacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearSimulacion() {

        Simulacion simulacion = new Simulacion();
        simulacion.setIdSimulacion(1L);

        when(simulacionRepository.save(simulacion)).thenReturn(simulacion);

        Simulacion result = simulacionService.crearSimulacion(simulacion);

        assertNotNull(result);
        assertEquals(1L, result.getIdSimulacion());
        verify(simulacionRepository).save(simulacion);
    }

    @Test
    void testObtenerPorId() {

        Simulacion simulacion = new Simulacion();
        simulacion.setIdSimulacion(1L);

        when(simulacionRepository.findById(1L))
                .thenReturn(Optional.of(simulacion));

        Optional<Simulacion> result = simulacionService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdSimulacion());
    }

    @Test
    void testEliminarSimulacion() {

        doNothing().when(simulacionRepository).deleteById(1L);

        simulacionService.eliminarSimulacion(1L);

        verify(simulacionRepository).deleteById(1L);
    }

    @Test
    void testListar() {

        List<SimulacionLightDTO> listaMock = List.of();

        when(simulacionRepository.findAllLight()).thenReturn(listaMock);

        List<SimulacionLightDTO> result = simulacionService.listar();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(simulacionRepository).findAllLight();
    }

    @Test
    void testObtenerFoto() {

        SimulacionHeavyDTO dto = new SimulacionHeavyDTO(1L, new byte[]{1,2,3}, "image/png");

        when(simulacionRepository.findFotoById(1L)).thenReturn(dto);

        SimulacionHeavyDTO result = simulacionService.obtenerFoto(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdSimulacion());
        assertEquals("image/png", result.getMimeType());
    }
}