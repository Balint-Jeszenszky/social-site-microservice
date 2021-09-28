package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.mapper.UserMapper;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.LoginCredentialsDto;
import hu.bme.aut.thesis.microservice.auth.models.LoginDetailsDto;
import hu.bme.aut.thesis.microservice.auth.models.NewTokenDto;
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
            throw new NoSuchElementException("User not found");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshJwt = jwtUtils.generateJwtRefreshToken(authentication);

        LoginDetailsDto loginDetailsDto = new LoginDetailsDto();

        User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        loginDetailsDto.setUserDetails(UserMapper.INSTANCE.userToUserDetailsDto(user));
        loginDetailsDto.setAccessToken(jwt);
        loginDetailsDto.setRefreshToken(refreshJwt);

        return loginDetailsDto;
    }

    public NewTokenDto refreshLogin(String token) {
        if (!jwtUtils.validateJwtRefreshToken(token)) {
            throw new IllegalArgumentException("Wrong refresh token");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshJwt = jwtUtils.generateJwtRefreshToken(authentication);

        NewTokenDto newTokenDto = new NewTokenDto();
        newTokenDto.setAccessToken(jwt);
        newTokenDto.setRefreshToken(refreshJwt);

        return newTokenDto;
    }
}
