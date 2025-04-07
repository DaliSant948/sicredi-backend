package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.VotoKafkaDTO;
import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.exception.SessaoEncerradaException;
import com.sicredi.desafio_sicredi.exception.SessaoNaoEncontradaException;
import com.sicredi.desafio_sicredi.exception.VotoDuplicadoException;
import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.model.Voto;
import com.sicredi.desafio_sicredi.repository.PautaRepository;
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

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoVotacaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    private SessaoVotacao sessao;
    private VotoRequestDTO request;
    private Pauta pauta;

    @BeforeEach
    void setUp() {
        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Pauta Teste");

        sessao = new SessaoVotacao();
        ReflectionTestUtils.setField(sessao, "id", 1L);
        sessao.setInicio(LocalDateTime.now().minusMinutes(5));
        sessao.setFim(LocalDateTime.now().plusMinutes(5));
        sessao.setPauta(pauta);

        request = new VotoRequestDTO("12345678900", OpcaoVoto.SIM);
    }

    @Test
    void deveRegistrarVotoComSucesso() {
        when(sessaoRepository.findByPautaId(1L)).thenReturn(Optional.of(sessao));
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(votoRepository.existsBySessaoVotacaoIdAndCpfAssociado(sessao.getId(), request.getCpfAssociado())).thenReturn(false);
        when(votoRepository.save(any())).thenAnswer(invocation -> {
            Voto v = invocation.getArgument(0);
            ReflectionTestUtils.setField(v, "id", 1L);
            return v;
        });

        VotoResponseDTO response = votoService.votar(1L, request);

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
