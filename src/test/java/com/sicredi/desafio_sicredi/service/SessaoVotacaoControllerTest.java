package com.sicredi.desafio_sicredi.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.desafio_sicredi.controller.SessaoVotacaoController;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoRequestDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoResponseDTO;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.desafio_sicredi.service.SessaoVotacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessaoVotacaoController.class)
public class SessaoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessaoVotacaoService sessaoService;

    @MockBean
    private SessaoVotacaoRepository sessaoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarNovaSessaoComSucesso() throws Exception {
        SessaoVotacaoRequestDTO request = new SessaoVotacaoRequestDTO(1L, 10);
        SessaoVotacaoResponseDTO response = new SessaoVotacaoResponseDTO(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                1L,
                false
        );

        when(sessaoService.abrirSessao(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSessao").value(1L))
                .andExpect(jsonPath("$.idPauta").value(1L));
    }

    @Test
    void deveListarTodasAsSessoes() throws Exception {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(1L);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(5));
        sessao.setEncerrada(false);
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        sessao.setPauta(pauta);

        when(sessaoRepository.findAll()).thenReturn(List.of(sessao));

        mockMvc.perform(get("/api/sessoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSessao").value(1L))
                .andExpect(jsonPath("$[0].idPauta").value(1L));
    }
}
