package com.sicredi.desafio_sicredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessaoEncerradaException extends RuntimeException {
    public SessaoEncerradaException(String message) {
        super(message);
    }
}
