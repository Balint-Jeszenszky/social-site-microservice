package hu.bme.aut.thesis.microservice.auth.controller.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AuthServiceException {

    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
