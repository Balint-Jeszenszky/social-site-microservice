package hu.bme.aut.thesis.microservice.social.security;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;

public interface UserAuthService {
    UserDetailsDto checkAuth(String accessToken);
}
