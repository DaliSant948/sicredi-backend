package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.exception.SessaoEncerradaException;
import com.sicredi.desafio_sicredi.exception.SessaoNaoEncontradaException;
import com.sicredi.desafio_sicredi.exception.VotoDuplicadoException;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.model.Voto;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.desafio_sicredi.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotoService {

    private static final Logger logger = LoggerFactory.getLogger(VotoService.class);

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoVotacaoRepository sessaoRepository;

    @Transactional
    public VotoResponseDTO votar(Long pautaId, VotoRequestDTO votoRequest) {

        SessaoVotacao sessao = sessaoRepository.findByPautaId(pautaId)
                .orElseThrow(() -> new SessaoNaoEncontradaException("Sessão não encontrada"));

        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(sessao.getInicio()) || agora.isAfter(sessao.getFim())) {
            throw new SessaoEncerradaException("Sessão não está aberta");
        }

        if (votoRepository.existsBySessaoVotacaoIdAndCpfAssociado(sessao.getId(), votoRequest.getCpfAssociado())) {
            throw new VotoDuplicadoException("CPF já votou nessa sessão");
        }

        Voto voto = new Voto();
        voto.setCpfAssociado(votoRequest.getCpfAssociado());
        voto.setOpcao(votoRequest.getOpcao());
        voto.setSessaoVotacao(sessao);

        voto = votoRepository.save(voto);

        logger.info("Voto registrado: Sessão {} - CPF {}", sessao.getId(), voto.getCpfAssociado());

        return new VotoResponseDTO(
                voto.getId(),
                voto.getCpfAssociado(),
                voto.getOpcao().toString(),
                pautaId
        );
    }
}
