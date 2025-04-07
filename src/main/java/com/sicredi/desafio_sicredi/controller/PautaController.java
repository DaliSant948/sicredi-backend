package com.sicredi.desafio_sicredi.controller;

import com.sicredi.desafio_sicredi.dto.PautaRequestDTO;
import com.sicredi.desafio_sicredi.dto.PautaResponseDTO;
import com.sicredi.desafio_sicredi.service.PautaService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.sicredi.desafio_sicredi.config.ApiPaths.BASE_V1;

@RestController
@RequestMapping(BASE_V1 + "/pautas")
public class PautaController {
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaResponseDTO> criarPauta(@Valid @RequestBody PautaRequestDTO dto) {
        PautaResponseDTO response = pautaService.criarPauta(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> buscarTodasPautas() {
        List<PautaResponseDTO> pautas = pautaService.buscarTodasPautas();
        return ResponseEntity.ok(pautas);
    }
}
