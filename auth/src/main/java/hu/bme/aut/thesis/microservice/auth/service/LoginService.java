package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.auth.controller.exceptions.UnauthorizedException;
import hu.bme.aut.thesis.microservice.auth.mapper.UserMapper;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.*;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.security.jwt.JwtUtils;
import hu.bme.aut.thesis.microservice.auth.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    public LoginDetailsDto login(LoginCredentialsDto loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("User not found");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails =  (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        String refreshJwt = jwtUtils.generateJwtRefreshToken(userDetails);

        LoginDetailsDto loginDetailsDto = new LoginDetailsDto();

        User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        loginDetailsDto.setUserDetails(UserMapper.INSTANCE.userToUserDetailsDto(user));
        loginDetailsDto.setAccessToken(jwt);
        loginDetailsDto.setRefreshToken(refreshJwt);

        return loginDetailsDto;
    }

    public NewTokenDto refreshLogin(String token) {
        if (!jwtUtils.validateJwtRefreshToken(token)) {
            throw new UnauthorizedException("Wrong refresh token");
        }

        String username = jwtUtils.getUserNameFromJwtRefreshToken(token);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User not fount"));

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        String jwt = jwtUtils.generateJwtToken(userDetails);
        String refreshJwt = jwtUtils.generateJwtRefreshToken(userDetails);

        NewTokenDto newTokenDto = new NewTokenDto();
        newTokenDto.setAccessToken(jwt);
        newTokenDto.setRefreshToken(refreshJwt);

        return newTokenDto;
    }

    public UserDetailsDto getUserDetailsByAccessToken(AccessTokenDto body) {
        if (!jwtUtils.validateJwtToken(body.getAccessToken())) {
            throw new UnauthorizedException("Wrong access token");
        }

        String username = jwtUtils.getUserNameFromJwtToken(body.getAccessToken());

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));

        return UserMapper.INSTANCE.userToUserDetailsDto(user);
    }
}
