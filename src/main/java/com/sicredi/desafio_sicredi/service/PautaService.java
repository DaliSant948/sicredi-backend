package com.sicredi.desafio_sicredi.service;


import com.sicredi.desafio_sicredi.dto.PautaRequestDTO;
import com.sicredi.desafio_sicredi.dto.PautaResponseDTO;
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

        // Cria o ResponseDTO para retornar os dados da pauta criada.
        return new PautaResponseDTO(pauta.getId(), pauta.getNome(), pauta.getDescricao());
    }
}
