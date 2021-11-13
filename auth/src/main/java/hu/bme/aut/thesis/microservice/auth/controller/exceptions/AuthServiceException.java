package hu.bme.aut.thesis.microservice.auth.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class AuthServiceException extends RuntimeException {

    public AuthServiceException(String errorMessage) {
        super(errorMessage);
    }

    public abstract HttpStatus getHttpStatus();
}
