package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.LoginApi;
import hu.bme.aut.thesis.microservice.auth.models.*;
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
    public ResponseEntity<UserDetailsDto> postLoginDetails(AccessTokenDto body) {
        UserDetailsDto userDetails = loginService.getUserDetailsByAccessToken(body);

        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewTokenDto> postLoginRefresh(RefreshTokenDto body) {
        NewTokenDto refreshTokenDto = loginService.refreshLogin(body.getToken());

        return new ResponseEntity(refreshTokenDto, HttpStatus.OK);
    }
}
