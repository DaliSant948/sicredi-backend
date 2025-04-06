package com.sicredi.desafio_sicredi.dto;

import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoKafkaDTO {

    private String cpfAssociado;
    private Long pautaId;
    private OpcaoVoto opcao;
    private LocalDateTime horario;
}
