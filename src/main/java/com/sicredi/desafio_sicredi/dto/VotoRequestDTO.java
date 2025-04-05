package com.sicredi.desafio_sicredi.dto;

import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequestDTO {

    @NotBlank(message = "CPF é obrigatório")
    private String cpfAssociado;

    @NotNull(message = "A opção de voto é obrigatória")
    private OpcaoVoto opcao;
}
