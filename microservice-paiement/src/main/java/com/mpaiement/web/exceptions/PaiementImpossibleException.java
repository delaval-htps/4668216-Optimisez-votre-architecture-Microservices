package com.mpaiement.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class PaiementImpossibleException extends RuntimeException {

    private final Long code;

    public PaiementImpossibleException(String message, Long code) {
        super(message);
        this.code = code;
    }
}
