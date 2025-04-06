package com.sicredi.desafio_sicredi.repository;

import com.sicredi.desafio_sicredi.model.OpcaoVoto;
import com.sicredi.desafio_sicredi.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>{
    boolean existsBySessaoVotacaoIdAndCpfAssociado(Long sessaoVotacaoId, String cpfAssociado);
    Long countBySessaoVotacaoIdAndOpcao(Long sessaoVotacaoId, OpcaoVoto opcao);
}
