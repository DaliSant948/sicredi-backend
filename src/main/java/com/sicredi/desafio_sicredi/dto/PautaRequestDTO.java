package com.sicredi.desafio_sicredi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaRequestDTO {

    @NotBlank(message = "O nome da pauta é obrigatório")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String nome;

    @Size(max = 500, message = "Máximo 500 caracteres")
    private String descricao;
}
