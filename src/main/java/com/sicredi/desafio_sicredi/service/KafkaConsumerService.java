package com.sicredi.desafio_sicredi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "topico-teste", groupId = "desafio-group")
    public void consumirMensagem(String mensagem) {
        logger.info("Mensagem recebida do Kafka: {}", mensagem);
    }
}
