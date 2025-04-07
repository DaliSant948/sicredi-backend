package com.sicredi.desafio_sicredi.controller;

import com.sicredi.desafio_sicredi.dto.SessaoVotacaoRequestDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoResponseDTO;
import com.sicredi.desafio_sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.desafio_sicredi.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoService;
    private final SessaoVotacaoRepository sessaoRepository;

    public SessaoVotacaoController(SessaoVotacaoService sessaoService, SessaoVotacaoRepository sessaoRepository) {
        this.sessaoService = sessaoService;
        this.sessaoRepository = sessaoRepository;
    }

    @PostMapping
    public ResponseEntity<SessaoVotacaoResponseDTO> abrirSessao(@Valid @RequestBody SessaoVotacaoRequestDTO dto) {
        SessaoVotacaoResponseDTO response = sessaoService.abrirSessao(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<SessaoVotacaoResponseDTO> listarTodas() {
        return sessaoRepository.findAll()
                .stream()
                .map(sessao -> new SessaoVotacaoResponseDTO(
                        sessao.getId(),
                        sessao.getInicio(),
                        sessao.getFim(),
                        sessao.getPauta().getId(),
                        sessao.isEncerrada()
                ))
                .collect(Collectors.toList());
    }
}
