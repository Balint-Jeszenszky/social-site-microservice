package hu.bme.aut.thesis.microservice.auth.controller.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AuthServiceException {

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
