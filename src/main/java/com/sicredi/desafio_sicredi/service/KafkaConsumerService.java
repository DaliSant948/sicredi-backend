package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.SessaoEncerradaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "topico-teste", groupId = "desafio-group")
    public void consumirMensagemSimples(String mensagem) {
        logger.info("Mensagem recebida do Kafka (topico-teste): {}", mensagem);
    }

    @KafkaListener(
            topics = "sessoes-encerradas",
            groupId = "desafio-group",
            containerFactory = "sessaoKafkaContainerFactory"
    )
    public void consumirSessaoEncerrada(SessaoEncerradaDTO dto) {
        logger.info("Sessão encerrada recebida do Kafka: Sessão {}, Pauta {}, Encerrada em {}",
                dto.getSessaoId(),
                dto.getPautaId(),
                dto.getHorarioEncerramento());
    }
}
