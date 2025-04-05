package com.sicredi.desafio_sicredi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sessao_votacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data/hora de início é obrigatória")
    private LocalDateTime inicio;

    @NotNull(message = "Data/hora de fim é obrigatória")
    private LocalDateTime fim;

    @OneToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @OneToMany(mappedBy = "sessaoVotacao", cascade = CascadeType.ALL)
    private List<Voto> votos;
}
