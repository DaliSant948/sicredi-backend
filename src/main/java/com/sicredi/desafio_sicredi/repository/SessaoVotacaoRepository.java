package com.sicredi.desafio_sicredi.repository;

import com.sicredi.desafio_sicredi.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
    // Métodos customizados, por exemplo, buscar por pauta, se necessário
    SessaoVotacao findByPautaId(Long pautaId);
}
