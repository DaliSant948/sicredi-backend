package com.sicredi.desafio_sicredi.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoVotacaoDTO {
    private Long pautaId;
    private Long totalVotosSim;
    private Long totalVotosNao;
    // Você pode incluir um campo para o status, como "APROVADA" ou "REJEITADA"
    private String resultado;
}
