package com.sicredi.desafio_sicredi.service;

import com.sicredi.desafio_sicredi.dto.PautaRequestDTO;
import com.sicredi.desafio_sicredi.dto.PautaResponseDTO;
import com.sicredi.desafio_sicredi.exception.PautaNaoEncontradaException;
import com.sicredi.desafio_sicredi.model.Pauta;
import com.sicredi.desafio_sicredi.repository.PautaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        LocalDateTime hora = LocalDateTime.now();
        pauta.setCreatedAt(hora);
        pauta.setUpdatedAt(hora);
        pauta = pautaRepository.save(pauta);

        return new PautaResponseDTO(pauta.getId(), pauta.getNome(), pauta.getDescricao());
    }

    public List<PautaResponseDTO> buscarTodasPautas() {
        List<Pauta> pautas = pautaRepository.findAll();
        return pautas.stream()
                     .map(pauta -> new PautaResponseDTO(pauta.getId(), pauta.getNome(), pauta.getDescricao()))
                     .collect(Collectors.toList());
    }
}
