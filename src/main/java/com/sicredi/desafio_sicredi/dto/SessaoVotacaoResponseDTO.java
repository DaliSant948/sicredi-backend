package com.sicredi.desafio_sicredi.dto;

import java.time.LocalDateTime;

public class SessaoVotacaoResponseDTO {

    private Long idSessao;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Long idPauta;
    private boolean encerrada;

    public Long getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Long idSessao) {
        this.idSessao = idSessao;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    public void setEncerrada(boolean encerrada) {
        this.encerrada = encerrada;
    }

    public SessaoVotacaoResponseDTO(Long idSessao, LocalDateTime inicio, LocalDateTime fim, Long idPauta, boolean encerrada) {
        this.idSessao = idSessao;
        this.inicio = inicio;
        this.fim = fim;
        this.idPauta = idPauta;
        this.encerrada = encerrada;
    }
}
