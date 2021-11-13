package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.ForgotPasswordApi;
import hu.bme.aut.thesis.microservice.auth.models.ForgotPasswordDto;
import hu.bme.aut.thesis.microservice.auth.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ForgotPasswordController implements ForgotPasswordApi {

    @Autowired
    private PasswordResetService passwordResetService;

    @Override
    public ResponseEntity<Void> postForgotPassword(ForgotPasswordDto body) {
        passwordResetService.resetPassword(body.getEmail());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
