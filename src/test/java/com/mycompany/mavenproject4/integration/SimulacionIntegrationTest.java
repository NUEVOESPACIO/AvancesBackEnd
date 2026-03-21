package com.mycompany.mavenproject4.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject4.dto.SimulacionRequestDTO;
import com.mycompany.mavenproject4.entidades.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SimulacionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // HELPER: crear usuario real
    // =========================
    private User crearUsuario() {
        User user = new User();

        // ⚠️ COMPLETAR según tu entidad real
        // Ejemplo:
        // user.setUsername("test");
        // user.setPassword("1234");

        em.persist(user);
        return user;
    }

    @Test
    void flujoCompleto_simulacion() throws Exception {

        // =========================
        // GIVEN
        // =========================
        User user = crearUsuario();

        byte[] imagen = new byte[]{1, 2, 3};
        String base64 = Base64.getEncoder().encodeToString(imagen);

        SimulacionRequestDTO request = new SimulacionRequestDTO();
        request.setNombre("Sim Test");
        request.setDescripcion("Desc Test");
        request.setFotoBase64(base64);
        request.setMimeType("image/png");

        // ⚠️ ajustar nombre del getter real
        request.setUserId(user.getId());

        // =========================
        // CREATE
        // =========================
        String response = mockMvc.perform(post("/abm/simulaciones/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();
        assertThat(id).isNotNull();

        // =========================
        // LISTAR (SIN depender del orden)
        // =========================
        mockMvc.perform(get("/abm/simulaciones/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nombre", hasItem("Sim Test")))
                .andExpect(jsonPath("$[*].descripcion", hasItem("Desc Test")));

        // =========================
        // GET FOTO
        // =========================
        mockMvc.perform(get("/abm/simulaciones/getfoto")
                        .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSimulacion").value(id))
                .andExpect(jsonPath("$.mimeType").value("image/png"));

        // =========================
        // DELETE
        // =========================
        mockMvc.perform(delete("/abm/simulaciones/eliminar")
                        .param("id", id.toString()))
                .andExpect(status().isOk());

        // =========================
        // VALIDAR QUE YA NO EXISTE
        // =========================
        mockMvc.perform(get("/abm/simulaciones/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].idSimulacion").value(org.hamcrest.Matchers.not(hasItem(id.intValue()))));
    }
}