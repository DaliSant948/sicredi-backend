package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.VotoKafkaDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VotoRealizadoConsumer {

    @KafkaListener(topics = "votacoes-realizadas", groupId = "desafio-group", containerFactory = "votoKafkaContainerFactory")
    public void consumirEvento(VotoKafkaDTO evento) {
        System.out.println("Evento recebido do Kafka:");
        System.out.println("Pauta: " + evento.getPautaId());
        System.out.println("CPF: " + evento.getCpfAssociado());
        System.out.println("Voto: " + evento.getOpcao());
        System.out.println("Data/hora: " + evento.getHorario());
    }
}
