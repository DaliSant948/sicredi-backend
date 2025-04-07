package com.sicredi.desafio_sicredi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.desafio_sicredi.controller.VotoController;
import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.exception.SessaoEncerradaException;
import com.sicredi.desafio_sicredi.exception.SessaoNaoEncontradaException;
import com.sicredi.desafio_sicredi.exception.VotoDuplicadoException;
import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Autowired
    private ObjectMapper objectMapper;

    public static final String BASE_V1 = "/api/v1";

    @Test
    void deveRegistrarVotoComSucesso() throws Exception {
        VotoRequestDTO request = new VotoRequestDTO("12345678900", OpcaoVoto.SIM);
        VotoResponseDTO response = new VotoResponseDTO(1L, "12345678900", "SIM", 1L);

        Mockito.when(votoService.votar(eq(1L), any(VotoRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post(BASE_V1 + "/votos/1")  
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) 
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.cpfAssociado").value("12345678900"))  
                .andExpect(jsonPath("$.voto").value("SIM")) 
                .andExpect(jsonPath("$.pautaId").value(1L));  
    }

    @Test
    void deveRetornar404QuandoSessaoNaoForEncontrada() throws Exception {
        VotoRequestDTO request = new VotoRequestDTO("12345678900", OpcaoVoto.SIM);

        Mockito.when(votoService.votar(eq(1L), any(VotoRequestDTO.class)))
                .thenThrow(new SessaoNaoEncontradaException("Sessão não encontrada"));

        mockMvc.perform(post(BASE_V1 + "/votos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoSessaoEstiverEncerrada() throws Exception {
        VotoRequestDTO request = new VotoRequestDTO("12345678900", OpcaoVoto.SIM);

        Mockito.when(votoService.votar(eq(1L), any(VotoRequestDTO.class)))
                .thenThrow(new SessaoEncerradaException("Sessão encerrada"));

        mockMvc.perform(post(BASE_V1 + "/votos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoVotoForDuplicado() throws Exception {
        VotoRequestDTO request = new VotoRequestDTO("12345678900", OpcaoVoto.SIM);

        Mockito.when(votoService.votar(eq(1L), any(VotoRequestDTO.class)))
                .thenThrow(new VotoDuplicadoException("Voto duplicado"));

        mockMvc.perform(post(BASE_V1 + "/votos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}
