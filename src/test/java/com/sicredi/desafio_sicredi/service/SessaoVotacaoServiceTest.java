package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.SessaoEncerradaDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoRequestDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoResponseDTO;
import com.sicredi.desafio_sicredi.exception.PautaNaoEncontradaException;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.repository.PautaRepository;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SessaoVotacaoServiceTest {

    @InjectMocks
    private SessaoVotacaoService sessaoService;

    @Mock
    private SessaoVotacaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private KafkaTemplate<String, SessaoEncerradaDTO> kafkaTemplate;

    @Test
    void deveEncerrarSessoesExpiradas() {
        // Arrange
        SessaoVotacao sessao = new SessaoVotacao();
        ReflectionTestUtils.setField(sessao, "id", 1L);
        sessao.setInicio(LocalDateTime.now().minusMinutes(10));
        sessao.setFim(LocalDateTime.now().minusMinutes(1));
        sessao.setEncerrada(false);

        Pauta pauta = new Pauta();
        pauta.setId(1L);
        sessao.setPauta(pauta);

        when(sessaoRepository.findAll()).thenReturn(List.of(sessao));

        // Act
        sessaoService.encerrarSessoesExpiradas();

        // Assert
        assertTrue(sessao.isEncerrada(), "Sessão deveria estar encerrada");
        verify(sessaoRepository).save(sessao);
        verify(kafkaTemplate).send(eq("sessoes-encerradas"), any(SessaoEncerradaDTO.class));
    }


    @Test
    void deveLancarExcecaoQuandoPautaNaoForEncontrada() {
        // Arrange
        Long pautaId = 1L;
        SessaoVotacaoRequestDTO requestDTO = new SessaoVotacaoRequestDTO(pautaId, 5);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PautaNaoEncontradaException.class, () -> {
            sessaoService.abrirSessao(requestDTO);
        });

        verify(pautaRepository).findById(pautaId);
        verify(sessaoRepository, never()).save(any());
    }
}
