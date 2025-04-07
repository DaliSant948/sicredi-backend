package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableJpaAuditing
public class PautaServiceTest {

    @Autowired
    private PautaRepository pautaRepository;

    private Pauta pauta;

    @BeforeEach
    void setUp() {
        pauta = new Pauta();
        pauta.setNome("Votação Plenária");
        pauta.setDescricao("Seleção de cargos");
    }

    @Test
    void testCriarPautaComAuditoria() {

        Pauta savedPauta = pautaRepository.save(pauta);

        assertNotNull(savedPauta.getCreatedAt(), "O campo 'createdAt' não pode ser nulo");
        assertNotNull(savedPauta.getUpdatedAt(), "O campo 'updatedAt' não pode ser nulo");

        assertEquals(savedPauta.getCreatedAt(), savedPauta.getUpdatedAt(), "O campo 'updatedAt' deve ser igual ao 'createdAt' na criação");
    }
}
