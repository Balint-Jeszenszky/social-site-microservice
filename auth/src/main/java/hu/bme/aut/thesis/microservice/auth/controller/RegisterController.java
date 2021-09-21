package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.RegisterApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController implements RegisterApi {
    @Override
    public ResponseEntity<Void> postRegister() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
