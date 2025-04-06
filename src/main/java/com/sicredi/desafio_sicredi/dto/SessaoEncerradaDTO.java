package com.sicredi.desafio_sicredi.dto;

import java.time.LocalDateTime;

public class SessaoEncerradaDTO {
    private Long sessaoId;
    private Long pautaId;
    private LocalDateTime horarioEncerramento;

    public SessaoEncerradaDTO() {}

    public SessaoEncerradaDTO(Long sessaoId, Long pautaId, LocalDateTime horarioEncerramento) {
        this.sessaoId = sessaoId;
        this.pautaId = pautaId;
        this.horarioEncerramento = horarioEncerramento;
    }

    public Long getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(Long sessaoId) {
        this.sessaoId = sessaoId;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public void setPautaId(Long pautaId) {
        this.pautaId = pautaId;
    }

    public LocalDateTime getHorarioEncerramento() {
        return horarioEncerramento;
    }

    public void setHorarioEncerramento(LocalDateTime horarioEncerramento) {
        this.horarioEncerramento = horarioEncerramento;
    }
}
