package com.clientui.clientui.exceptions.handler_exception;

import java.util.Locale;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.clientui.clientui.exceptions.PaiementConflictException;

@ControllerAdvice
public class GlobalHandlerException extends  ResponseEntityExceptionHandler{
    

    @ExceptionHandler(PaiementConflictException.class)
    public String handlePaiementConflictException(PaiementConflictException e, Locale Locale) {

        // on  retourne ici une vue pour pouvoir afficher "paiement non abouti" si le microservice paiement a renvoyer une erreur
        return "Confirmation";
    }
}
