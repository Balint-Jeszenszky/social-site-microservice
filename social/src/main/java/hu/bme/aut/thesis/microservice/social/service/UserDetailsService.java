package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;

import java.util.Optional;

public interface UserDetailsService {
    Optional<UserDetailsDto> getUserDetailsById(Integer userId);
}
