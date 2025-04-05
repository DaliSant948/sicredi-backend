package com.sicredi.desafio_sicredi.controller;

import com.sicredi.desafio_sicredi.dto.SessaoVotacaoRequestDTO;
import com.sicredi.desafio_sicredi.dto.SessaoVotacaoResponseDTO;
import com.sicredi.desafio_sicredi.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoService;


    public SessaoVotacaoController(SessaoVotacaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<SessaoVotacaoResponseDTO> abrirSessao(@Valid @RequestBody SessaoVotacaoRequestDTO dto) {
        SessaoVotacaoResponseDTO response = sessaoService.abrirSessao(dto);
        return ResponseEntity.ok(response);
    }
}
