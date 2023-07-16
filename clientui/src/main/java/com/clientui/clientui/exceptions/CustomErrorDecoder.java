package com.clientui.clientui.exceptions;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
       
        String initialErrorMessage = null;

        try (InputStream bodyIs = response.body().asInputStream()) {

            ObjectMapper mapper = new ObjectMapper();

            ExceptionMessage message = mapper.readValue(bodyIs, ExceptionMessage.class);
            
            initialErrorMessage = message.getMessage();

        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        switch (response.status()) {

            case 400:
                return new ProductBadRequestException(
                        initialErrorMessage != null ? initialErrorMessage : "requête incorrecte");
            case 409:
                return new PaiementConflictException(
                        initialErrorMessage != null ? initialErrorMessage : "paiement impossible");
                
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class ExceptionMessage {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Long code;

    }
}
