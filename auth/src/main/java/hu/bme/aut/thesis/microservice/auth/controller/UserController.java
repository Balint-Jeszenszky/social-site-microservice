package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.UserApi;
import hu.bme.aut.thesis.microservice.auth.mapper.UserMapper;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.auth.models.UserEditDto;
import hu.bme.aut.thesis.microservice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Void> deleteUserId(Integer id) {
        userService.deleteUserById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserDetailsDto> getUser(Integer id) {
        User user = userService.getUserById(id);

        UserDetailsDto userDetailsDto = UserMapper.INSTANCE.userToUserDetailsDto(user);

        return new ResponseEntity(userDetailsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDetailsDto> putUserId(Integer id, UserEditDto body) {
        User user = userService.editUser(id, body);

        UserDetailsDto userDetailsDto = UserMapper.INSTANCE.userToUserDetailsDto(user);

        return new ResponseEntity(userDetailsDto, HttpStatus.ACCEPTED);
    }
}
