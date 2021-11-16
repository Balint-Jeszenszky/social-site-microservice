package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.PasswordResetApi;
import hu.bme.aut.thesis.microservice.auth.models.ForgotPasswordDto;
import hu.bme.aut.thesis.microservice.auth.models.PasswordResetDto;
import hu.bme.aut.thesis.microservice.auth.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PasswordResetController implements PasswordResetApi {

    @Autowired
    private PasswordResetService passwordResetService;

    @Override
    public ResponseEntity<Void> postForgotPassword(ForgotPasswordDto body) {
        passwordResetService.sendResetMail(body.getEmail());

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> putForgotPassword(PasswordResetDto body) {
        passwordResetService.resetPassword(body);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
