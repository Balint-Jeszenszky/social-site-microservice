package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.PublicUserApi;
import hu.bme.aut.thesis.microservice.auth.mapper.UserMapper;
import hu.bme.aut.thesis.microservice.auth.models.PublicUserDetailsDto;
import hu.bme.aut.thesis.microservice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PublicUserDetailsController extends GlobalExceptionHandler implements PublicUserApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<PublicUserDetailsDto> getPublicUser(Integer id) {
        return new ResponseEntity(UserMapper.INSTANCE.userToPublicUserDetailsDto(userService.getUserById(id)), HttpStatus.OK);
    }
}
