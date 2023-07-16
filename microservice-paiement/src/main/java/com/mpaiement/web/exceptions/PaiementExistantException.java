package com.mpaiement.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class PaiementExistantException extends RuntimeException {

    private final Long code;

    public PaiementExistantException(String message, Long code) {
        super(message);
        this.code = code;
    }
}
