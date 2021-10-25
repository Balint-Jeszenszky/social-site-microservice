package hu.bme.aut.thesis.microservice.social.controller.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends SocialServiceException {

    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
