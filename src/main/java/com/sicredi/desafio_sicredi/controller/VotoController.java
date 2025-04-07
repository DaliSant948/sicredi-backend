package com.sicredi.desafio_sicredi.controller;

import com.sicredi.desafio_sicredi.dto.ResultadoVotacaoDTO;
import com.sicredi.desafio_sicredi.dto.VotoRequestDTO;
import com.sicredi.desafio_sicredi.dto.VotoResponseDTO;
import com.sicredi.desafio_sicredi.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.sicredi.desafio_sicredi.config.ApiPaths.BASE_V1;

@RestController
@RequestMapping(BASE_V1 + "/votos")
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

    @GetMapping("/resultado/{pautaId}")
    public ResponseEntity<ResultadoVotacaoDTO> obterResultado(@PathVariable Long pautaId) {
        ResultadoVotacaoDTO resultado = votoService.calcularResultado(pautaId);
        return ResponseEntity.ok(resultado);
    }
}
