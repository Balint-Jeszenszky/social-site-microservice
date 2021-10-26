package hu.bme.aut.thesis.microservice.social.controller.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends SocialServiceException {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
