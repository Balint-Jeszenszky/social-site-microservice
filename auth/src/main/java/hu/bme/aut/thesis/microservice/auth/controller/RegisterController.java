package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.RegisterApi;
import hu.bme.aut.thesis.microservice.auth.mapper.UserMapper;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.RegisterDto;
import hu.bme.aut.thesis.microservice.auth.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.auth.service.EmailVerificationService;
import hu.bme.aut.thesis.microservice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends GlobalExceptionHandler implements RegisterApi {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Override
    public ResponseEntity<Void> getRegisterValidate(String key) {
        emailVerificationService.validate(key);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<UserDetailsDto> postRegister(RegisterDto registerDto) {
        User user = userService.registerUser(registerDto);

        UserDetailsDto userDetailsDto = UserMapper.INSTANCE.userToUserDetailsDto(user);

        return new ResponseEntity(userDetailsDto, HttpStatus.CREATED);
    }
}
