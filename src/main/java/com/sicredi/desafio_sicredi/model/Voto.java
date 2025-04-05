package com.sicredi.desafio_sicredi.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "voto", uniqueConstraints = {@UniqueConstraint(columnNames = {"sessao_votacao_id", "cpf_associado"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CPF do associado é obrigatório")
    @Column(name = "cpf_associado")
    private String cpfAssociado;

    @NotNull(message = "Opção de voto é obrigatória")
    @Enumerated(EnumType.STRING)
    private OpcaoVoto opcao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_votacao_id", nullable = false)
    private SessaoVotacao sessaoVotacao;
}
