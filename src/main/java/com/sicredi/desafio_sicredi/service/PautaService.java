package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.PautaRequestDTO;
import com.sicredi.desafio_sicredi.dto.PautaResponseDTO;
import com.sicredi.desafio_sicredi.exception.PautaNaoEncontradaException;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {
    @Autowired
    private PautaRepository pautaRepository;

    public PautaResponseDTO criarPauta(PautaRequestDTO pautaRequestDTO) {
        Pauta pauta = new Pauta();
        pauta.setNome(pautaRequestDTO.getNome());
        pauta.setDescricao(pautaRequestDTO.getDescricao());

        pauta = pautaRepository.save(pauta);

        return new PautaResponseDTO(pauta.getId(), pauta.getNome(), pauta.getDescricao());
    }

    public Pauta buscarPautaPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new PautaNaoEncontradaException("Pauta com ID " + id + " não encontrada"));
    }
}
