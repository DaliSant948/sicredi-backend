package com.sicredi.desafio_sicredi.controller;

import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votos")
public class VotoController {
    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping("/{pautaId}")
    public ResponseEntity<VotoResponseDTO> votar(@PathVariable Long pautaId,
                                                 @Valid @RequestBody VotoRequestDTO dto) {
        VotoResponseDTO response = votoService.votar(pautaId, dto);
        return ResponseEntity.ok(response);
    }
}
