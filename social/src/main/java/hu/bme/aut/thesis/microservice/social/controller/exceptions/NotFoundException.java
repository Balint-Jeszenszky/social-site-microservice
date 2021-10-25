package hu.bme.aut.thesis.microservice.social.controller.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends SocialServiceException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
