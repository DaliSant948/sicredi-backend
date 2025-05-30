package com.sicredi.desafio_sicredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SessaoNaoEncontradaException extends RuntimeException {
    public SessaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
