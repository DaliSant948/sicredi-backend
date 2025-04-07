package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.ResultadoVotacaoDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public VotoResponseDTO votar(Long pautaId, VotoRequestDTO votoRequest) {
        SessaoVotacao sessao = sessaoVotacaoRepository.findByPautaId(pautaId)
                .orElseThrow(() -> new SessaoNaoEncontradaException("Sessão de votação não encontrada para a pauta."));

        if (sessao.isEncerrada() || LocalDateTime.now().isAfter(sessao.getFim())) {
            throw new SessaoEncerradaException("Sessão de votação encerrada.");
        }

        boolean votoJaRegistrado = votoRepository.existsBySessaoVotacaoIdAndCpfAssociado(
                sessao.getId(), votoRequest.getCpfAssociado());

        if (votoJaRegistrado) {
            throw new VotoDuplicadoException("Associado já votou nesta sessão.");
        }

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada."));

        Voto voto = new Voto();
        voto.setCpfAssociado(votoRequest.getCpfAssociado());
        voto.setOpcao(votoRequest.getOpcao());
        voto.setSessaoVotacao(sessao);

        Voto salvo = votoRepository.save(voto);

        VotoKafkaDTO votoKafkaDTO = new VotoKafkaDTO(
                salvo.getCpfAssociado(),
                salvo.getSessaoVotacao().getPauta().getId(),
                salvo.getOpcao(),
                LocalDateTime.now()
        );
        kafkaProducerService.enviarEvento("votacoes-realizadas", votoKafkaDTO);

        return new VotoResponseDTO(
                salvo.getId(),
                salvo.getCpfAssociado(),
                salvo.getOpcao().toString(),
                salvo.getSessaoVotacao().getPauta().getId()
        );
    }

    public ResultadoVotacaoDTO calcularResultado(Long pautaId) {
        SessaoVotacao sessao = sessaoVotacaoRepository.findByPautaId(pautaId)
                .orElseThrow(() -> new SessaoNaoEncontradaException("Sessão de votação não encontrada."));

        Long totalSim = votoRepository.countBySessaoVotacaoIdAndOpcao(sessao.getId(), OpcaoVoto.SIM);
        Long totalNao = votoRepository.countBySessaoVotacaoIdAndOpcao(sessao.getId(), OpcaoVoto.NAO);

        ResultadoVotacaoDTO.StatusVotacao status;
        if (totalSim > totalNao) {
            status = ResultadoVotacaoDTO.StatusVotacao.APROVADA;
        } else if (totalNao > totalSim) {
            status = ResultadoVotacaoDTO.StatusVotacao.REPROVADA;
        } else {
            status = ResultadoVotacaoDTO.StatusVotacao.EMPATE;
        }

        return new ResultadoVotacaoDTO(pautaId, totalSim, totalNao, status);
    }
}