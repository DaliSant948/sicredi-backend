package com.sicredi.desafio_sicredi.dto;

public class VotoResponseDTO {

    private Long id;
    private String cpfAssociado;
    private String voto;
    private Long pautaId;

    public VotoResponseDTO() {
    }

    public VotoResponseDTO(Long id, String cpfAssociado, String voto, Long pautaId) {
        this.id = id;
        this.cpfAssociado = cpfAssociado;
        this.voto = voto;
        this.pautaId = pautaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfAssociado() {
        return cpfAssociado;
    }

    public void setCpfAssociado(String cpfAssociado) {
        this.cpfAssociado = cpfAssociado;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public void setPautaId(Long pautaId) {
        this.pautaId = pautaId;
    }
}
