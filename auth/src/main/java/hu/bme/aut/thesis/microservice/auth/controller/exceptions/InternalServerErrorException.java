package hu.bme.aut.thesis.microservice.auth.controller.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AuthServiceException {
    public InternalServerErrorException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
