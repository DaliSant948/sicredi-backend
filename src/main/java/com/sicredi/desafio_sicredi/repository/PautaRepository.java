package com.sicredi.desafio_sicredi.repository;

import com.sicredi.desafio_sicredi.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Optional<Pauta> findByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
