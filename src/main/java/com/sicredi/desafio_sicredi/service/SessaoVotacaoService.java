package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.SessaoEncerradaDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoRequestDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoResponseDTO;
import com.sicredi.desafio_sicredi.exception.PautaNaoEncontradaException;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.repository.PautaRepository;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoVotacaoService {
    @Autowired
    private SessaoVotacaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public SessaoVotacaoResponseDTO abrirSessao(SessaoVotacaoRequestDTO sessaoRequest) {

        Pauta pauta = pautaRepository.findById(sessaoRequest.getPautaId())
                .orElseThrow(() -> new PautaNaoEncontradaException("Pauta com ID " + sessaoRequest.getPautaId() + " não encontrada"));

        SessaoVotacao sessao = new SessaoVotacao();
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(sessaoRequest.getDuracao());

        sessao.setInicio(inicio);
        sessao.setFim(fim);
        sessao.setPauta(pauta);
        sessao.setUpdatedAt(LocalDateTime.now());
        SessaoVotacao sessaoSalva = sessaoRepository.save(sessao);

        return new SessaoVotacaoResponseDTO(
                sessaoSalva.getId(),
                sessaoSalva.getInicio(),
                sessaoSalva.getFim(),
                pauta.getId(),
                sessao.isEncerrada());
    }

    public void encerrarSessoesExpiradas() {
        List<SessaoVotacao> sessoesAtivas = sessaoRepository.findAll();

        for (SessaoVotacao sessao : sessoesAtivas) {
            if (!sessao.isEncerrada() && LocalDateTime.now().isAfter(sessao.getFim())) {
                sessao.setEncerrada(true);
                sessaoRepository.save(sessao);

                SessaoEncerradaDTO evento = new SessaoEncerradaDTO(
                        sessao.getId(),
                        sessao.getPauta().getId(),
                        sessao.getFim()
                );
                kafkaTemplate.send("sessoes-encerradas", evento);
            }
        }
    }
}
