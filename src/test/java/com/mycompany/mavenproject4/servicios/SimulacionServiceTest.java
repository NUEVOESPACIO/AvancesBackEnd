package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.repository.SimulacionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacionServiceTest {

    @Mock
    private SimulacionRepository simulacionRepository;

    @InjectMocks
    private SimulacionService simulacionService;

    @Test
    void testCrearSimulacion() {

        Simulacion simulacion = new Simulacion();

        when(simulacionRepository.save(simulacion)).thenReturn(simulacion);

        Simulacion resultado = simulacionService.crearSimulacion(simulacion);

        assertNotNull(resultado);
        verify(simulacionRepository).save(simulacion);
    }

    @Test
    void testObtenerTodas() {

        List<Simulacion> lista = List.of(new Simulacion(), new Simulacion());

        when(simulacionRepository.findAll()).thenReturn(lista);

        List<Simulacion> resultado = simulacionService.obtenerTodas();

        assertEquals(2, resultado.size());
    }

    @Test
    void testObtenerPorId() {

        Simulacion simulacion = new Simulacion();

        when(simulacionRepository.findById(1L)).thenReturn(Optional.of(simulacion));

        Optional<Simulacion> resultado = simulacionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void testEliminar() {

        simulacionService.eliminar(1L);

        verify(simulacionRepository).deleteById(1L);
    }
}