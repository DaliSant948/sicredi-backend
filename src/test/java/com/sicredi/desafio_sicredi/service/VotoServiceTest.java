package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.VotoKafkaDTO;
import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.exception.SessaoEncerradaException;
import com.sicredi.desafio_sicredi.exception.SessaoNaoEncontradaException;
import com.sicredi.desafio_sicredi.exception.VotoDuplicadoException;
import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.model.Voto;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.desafio_sicredi.repository.VotoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoVotacaoRepository sessaoRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    private SessaoVotacao sessao;
    private VotoRequestDTO request;

    @BeforeEach
    void setUp() {
        sessao = new SessaoVotacao();
        ReflectionTestUtils.setField(sessao, "id", 1L);
        sessao.setInicio(LocalDateTime.now().minusMinutes(5));
        sessao.setFim(LocalDateTime.now().plusMinutes(5));

        request = new VotoRequestDTO("12345678900", OpcaoVoto.SIM);
    }

    @Test
    void deveRegistrarVotoComSucesso() {
        // Arrange
        when(sessaoRepository.findByPautaId(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoVotacaoIdAndCpfAssociado(sessao.getId(), request.getCpfAssociado())).thenReturn(false);
        when(votoRepository.save(any())).thenAnswer(invocation -> {
            Voto v = invocation.getArgument(0);
            ReflectionTestUtils.setField(v, "id", 1L);
            return v;
        });

        // Act
        VotoResponseDTO response = votoService.votar(1L, request);

        // Assert
        assertNotNull(response);
        assertEquals("12345678900", response.getCpfAssociado());
        assertEquals("SIM", response.getVoto());
        assertEquals(1L, response.getPautaId());
        verify(kafkaProducerService).enviarEvento(eq("votacoes-realizadas"), any(VotoKafkaDTO.class));
    }

    @Test
    void deveLancarExcecaoQuandoSessaoNaoForEncontrada() {
        when(sessaoRepository.findByPautaId(1L)).thenReturn(Optional.empty());

        assertThrows(SessaoNaoEncontradaException.class, () -> votoService.votar(1L, request));
    }

    @Test
    void deveLancarExcecaoQuandoSessaoEstiverEncerrada() {
        sessao.setInicio(LocalDateTime.now().minusMinutes(10));
        sessao.setFim(LocalDateTime.now().minusMinutes(1));

        when(sessaoRepository.findByPautaId(1L)).thenReturn(Optional.of(sessao));

        assertThrows(SessaoEncerradaException.class, () -> votoService.votar(1L, request));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaVotou() {
        when(sessaoRepository.findByPautaId(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoVotacaoIdAndCpfAssociado(sessao.getId(), request.getCpfAssociado())).thenReturn(true);

        assertThrows(VotoDuplicadoException.class, () -> votoService.votar(1L, request));
    }
}
