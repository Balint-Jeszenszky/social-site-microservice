package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.SocialServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SocialServiceException.class)
    public ResponseEntity<String> handleExceptions(SocialServiceException e) {
        return new ResponseEntity(e.getMessage(), e.getHttpStatus());
    }
}
