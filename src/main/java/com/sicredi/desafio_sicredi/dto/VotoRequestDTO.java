package com.sicredi.desafio_sicredi.dto;

import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequestDTO {

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF inválido")
    private String cpfAssociado;

    @NotNull(message = "A opção de voto é obrigatória")
    private OpcaoVoto opcao;
}
