package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.SessaoEncerradaDTO;
import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SessaoScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SessaoScheduler.class);

    private final SessaoVotacaoRepository sessaoRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public SessaoScheduler(SessaoVotacaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void verificarSessoesEncerradas() {
        List<SessaoVotacao> sessoes = sessaoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();

        sessoes.stream()
                .filter(sessao -> agora.isAfter(sessao.getFim()) && !sessao.isEncerrada())
                .forEach(sessao -> {
                    logger.info("Sessão {} encerrada automaticamente.", sessao.getId());
                    sessao.setEncerrada(true);
                    sessaoRepository.save(sessao);

                    SessaoEncerradaDTO dto = new SessaoEncerradaDTO(
                            sessao.getId(),
                            sessao.getPauta().getId(),
                            LocalDateTime.now()
                    );
                    kafkaProducerService.enviarEvento("sessoes-encerradas", dto);
                });
    }
}
