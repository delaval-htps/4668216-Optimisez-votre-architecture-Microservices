package com.mpaiement.web.exceptions.handler_exception;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mpaiement.web.exceptions.PaiementExistantException;
import com.mpaiement.web.exceptions.PaiementImpossibleException;
import com.mpaiement.web.exceptions.config.ErrorResponse;

@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PaiementExistantException.class)
    protected ResponseEntity<ErrorResponse> handlePaiementExistantException(PaiementExistantException e, Locale locale) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(PaiementImpossibleException.class)
    protected ResponseEntity<ErrorResponse> handlePaiementImpossibleException(PaiementImpossibleException e, Locale locale) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<String> handleException(Exception e, Locale locale) {
        return ResponseEntity
            .badRequest()
            .body("Exception occured inside API "+ e);
    }

}
