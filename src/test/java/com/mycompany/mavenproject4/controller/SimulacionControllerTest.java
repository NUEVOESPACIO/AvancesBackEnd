package com.mycompany.mavenproject4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject4.dto.SimulacionHeavyDTO;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.SimulacionService;
import com.mycompany.mavenproject4.servicios.UserService;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SimulacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulacionService simulacionService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearSimulacion() throws Exception {

        // DTO que enviará el test
        SimulacionRequestDTO request = new SimulacionRequestDTO();
        request.setNombre("Test simulacion");
        request.setDescripcion("Descripcion test");
        request.setUserId(1L);

        // usuario mock
        User user = new User();
        user.setId(1L);

        // simulacion mock
        Simulacion simulacion = new Simulacion();
        simulacion.setIdSimulacion(10L);

        when(userService.obtenerPorId(1L)).thenReturn(Optional.of(user));
        when(simulacionService.crearSimulacion(org.mockito.ArgumentMatchers.any()))
                .thenReturn(simulacion);

        mockMvc.perform(post("/abm/simulaciones/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar() throws Exception {
        mockMvc.perform(delete("/abm/simulaciones/eliminar")
                .param("id", "1"))
                .andExpect(status().isOk());
    }
    
        @Test
    void testListar() throws Exception {

        when(simulacionService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/abm/simulaciones/listar"))
                .andExpect(status().isOk());
    }
    
        @Test
    void testEditarParcial() throws Exception {

        SimulacionRequestDTO request = new SimulacionRequestDTO();
        request.setNombre("Nuevo nombre");

        Simulacion simulacion = new Simulacion();
        simulacion.setIdSimulacion(1L);

        when(simulacionService.obtenerPorId(1L))
                .thenReturn(Optional.of(simulacion));

        when(simulacionService.crearSimulacion(any()))
                .thenReturn(simulacion);

        mockMvc.perform(patch("/abm/simulaciones/editar")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    
        @Test
    void testObtenerFoto() throws Exception {

        SimulacionHeavyDTO dto = new SimulacionHeavyDTO();

        when(simulacionService.obtenerFoto(1L)).thenReturn(dto);

        mockMvc.perform(get("/abm/simulaciones/getfoto")
                .param("id", "1"))
                .andExpect(status().isOk());
    }
}
