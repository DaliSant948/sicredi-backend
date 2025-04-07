package com.sicredi.desafio_sicredi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoVotacaoRequestDTO {

    @NotNull(message = "O ID da pauta é obrigatório")
    private Long pautaId;

    private Integer duracao = 1;
}
