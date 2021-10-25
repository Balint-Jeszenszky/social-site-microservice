package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.SocialServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

public abstract class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(SocialServiceException e) {
        return new ResponseEntity(e.getMessage(), e.getHttpStatus());
    }
}
