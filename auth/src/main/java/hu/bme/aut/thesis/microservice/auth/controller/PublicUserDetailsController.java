package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.api.PublicUserApi;
import hu.bme.aut.thesis.microservice.auth.mapper.UserMapper;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.PublicUserDetailsDto;
import hu.bme.aut.thesis.microservice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PublicUserDetailsController implements PublicUserApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<PublicUserDetailsDto> getPublicUser(Integer id) {
        return new ResponseEntity(UserMapper.INSTANCE.userToPublicUserDetailsDto(userService.getUserById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PublicUserDetailsDto> getPublicUserFindUsername(String username) {
        User user = userService.getUserByUsername(username);

        PublicUserDetailsDto userDetailsDto = UserMapper.INSTANCE.userToPublicUserDetailsDto(user);

        return new ResponseEntity(userDetailsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PublicUserDetailsDto>> getPublicUserSearchQuery(String query) {
        List<User> users = userService.searchUsers(query);

        List<PublicUserDetailsDto> publicUsers = users.stream().map(u -> UserMapper.INSTANCE.userToPublicUserDetailsDto(u)).collect(Collectors.toList());

        return new ResponseEntity(publicUsers, HttpStatus.OK);
    }
}
