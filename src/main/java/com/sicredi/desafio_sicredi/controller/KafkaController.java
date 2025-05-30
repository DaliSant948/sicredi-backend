package com.sicredi.desafio_sicredi.controller;

import com.sicredi.desafio_sicredi.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.sicredi.desafio_sicredi.config.ApiPaths.BASE_V1;


@RestController
@RequestMapping(BASE_V1 + "/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> enviarMensagem(@RequestParam String mensagem) {
        kafkaProducerService.enviarEvento("topico-teste", mensagem);
        return ResponseEntity.ok("Mensagem enviada: " + mensagem);
    }
}
