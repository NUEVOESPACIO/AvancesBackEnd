package com.mycompany.mavenproject4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.entidades.Simulacion;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.servicios.SimulacionService;
import com.mycompany.mavenproject4.servicios.UserService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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
}