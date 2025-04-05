package com.sicredi.desafio_sicredi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaRequestDTO {

    @NotBlank(message = "O nome da pauta é obrigatório")
    private String nome;
    private String descricao;
}
