package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.model.Voto;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.desafio_sicredi.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoVotacaoRepository sessaoRepository;

//    @Autowired
//    private VotoProducer votoProducer;  // Injeção do produtor Kafka

    public VotoResponseDTO votar(Long pautaId, VotoRequestDTO votoRequest) {
        // Busca a sessão de votação para a pauta
        SessaoVotacao sessao = sessaoRepository.findByPautaId(pautaId);
        if (sessao == null) {
            throw new RuntimeException("Sessão de votação não encontrada para essa pauta");
        }

        // Verifica se o CPF já votou na sessão
        if (votoRepository.existsBySessaoVotacaoIdAndCpfAssociado(sessao.getId(), votoRequest.getCpfAssociado())) {
            throw new RuntimeException("Associado já votou nessa sessão");
        }

        Voto voto = new Voto();
        voto.setCpfAssociado(votoRequest.getCpfAssociado());
        voto.setOpcao(votoRequest.getOpcao());
        voto.setSessaoVotacao(sessao);

        Voto votoSalvo = votoRepository.save(voto);
        // Enviar mensagem ao Kafka informando que um novo voto foi registrado
       // votoProducer.enviarMensagem("Novo voto registrado para a pauta " + pautaId);

        return new VotoResponseDTO(
                votoSalvo.getId(),
                votoSalvo.getCpfAssociado(),
                votoSalvo.getOpcao().toString(),
                pautaId
        );
    }
}
