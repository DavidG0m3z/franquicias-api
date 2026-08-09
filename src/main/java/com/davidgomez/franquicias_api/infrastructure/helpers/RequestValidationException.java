package com.davidgomez.franquicias_api.infrastructure.helpers;

public class RequestValidationException extends RuntimeException{

    public RequestValidationException(String message) {
        super(message);
    }
}
