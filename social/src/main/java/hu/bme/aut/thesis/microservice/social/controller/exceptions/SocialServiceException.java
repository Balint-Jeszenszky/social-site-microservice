package hu.bme.aut.thesis.microservice.social.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class SocialServiceException extends RuntimeException {

    public SocialServiceException(String errorMessage) {
        super(errorMessage);
    }

    public abstract HttpStatus getHttpStatus();
}
