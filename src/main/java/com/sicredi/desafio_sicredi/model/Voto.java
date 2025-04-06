package com.sicredi.desafio_sicredi.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voto",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sessao_votacao_id", "cpf_associado"}),
        indexes = {
                @Index(columnList = "cpf_associado"),
                @Index(columnList = "sessao_votacao_id")
        })
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CPF do associado é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    @Column(name = "cpf_associado", nullable = false, length = 11)
    private String cpfAssociado;

    @NotNull(message = "Opção de voto é obrigatória")
    @Enumerated(EnumType.STRING)
    private OpcaoVoto opcao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_votacao_id", nullable = false)
    private SessaoVotacao sessaoVotacao;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
