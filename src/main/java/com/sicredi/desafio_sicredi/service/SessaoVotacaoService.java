package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.SessaoVotacaoRequestDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoResponseDTO;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.repository.PautaRepository;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoVotacaoService {
    @Autowired
    private SessaoVotacaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public SessaoVotacaoResponseDTO abrirSessao(SessaoVotacaoRequestDTO sessaoRequest) {
        // Busca a pauta associada
        Pauta pauta = pautaRepository.findById(sessaoRequest.getPautaId())
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        SessaoVotacao sessao = new SessaoVotacao();
        LocalDateTime inicio = LocalDateTime.now();
        // Define o fim da sessão adicionando a duração (em minutos)
        LocalDateTime fim = inicio.plusMinutes(sessaoRequest.getDuracao());
        sessao.setInicio(inicio);
        sessao.setFim(fim);
        sessao.setPauta(pauta);

        SessaoVotacao sessaoSalva = sessaoRepository.save(sessao);

        return new SessaoVotacaoResponseDTO(
                sessaoSalva.getId(),
                sessaoSalva.getInicio(),
                sessaoSalva.getFim(),
                pauta.getId()
        );
    }
}
