package com.sicredi.desafio_sicredi.repository;

import com.sicredi.desafio_sicredi.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    // Métodos customizados (se necessário) podem ser adicionados aqui
}
