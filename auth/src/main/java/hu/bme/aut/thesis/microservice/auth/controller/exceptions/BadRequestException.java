package hu.bme.aut.thesis.microservice.auth.controller.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AuthServiceException {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
