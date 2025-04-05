package com.sicredi.desafio_sicredi.repository;

import com.sicredi.desafio_sicredi.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>{
    // Verifica se um CPF já votou em uma sessão específica
    boolean existsBySessaoVotacaoIdAndCpfAssociado(Long sessaoVotacaoId, String cpfAssociado);

    // Também pode ser útil contar os votos de uma sessão
    Long countBySessaoVotacaoIdAndOpcao(Long sessaoVotacaoId, com.sicredi.desafio_sicredi.model.Voto voto);
}
