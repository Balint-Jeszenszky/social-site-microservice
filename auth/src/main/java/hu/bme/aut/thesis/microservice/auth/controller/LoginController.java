package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.LoginApi;
import hu.bme.aut.thesis.microservice.auth.models.LoginCredentialsDto;
import hu.bme.aut.thesis.microservice.auth.models.LoginDetailsDto;
import hu.bme.aut.thesis.microservice.auth.models.NewTokenDto;
import hu.bme.aut.thesis.microservice.auth.models.RefreshTokenDto;
import hu.bme.aut.thesis.microservice.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends GlobalExceptionHandler implements LoginApi {

    @Autowired
    private LoginService loginService;

    @Override
    public ResponseEntity<LoginDetailsDto> postLogin(LoginCredentialsDto body) {
        LoginDetailsDto loginDetailsDto = loginService.login(body);

        return new ResponseEntity(loginDetailsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewTokenDto> postLoginRefresh(RefreshTokenDto body) {
        NewTokenDto refreshTokenDto = loginService.refreshLogin(body.getToken());

        return new ResponseEntity(refreshTokenDto, HttpStatus.OK);
    }
}
