package hu.bme.aut.thesis.microservice.social.util;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.security.UserAuthService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MockUserAuthService implements UserAuthService {

    @Override
    public UserDetailsDto checkAuth(String accessToken) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(1);
        userDetailsDto.setEmail("asd@asd.asd");
        userDetailsDto.setUsername("user");
        userDetailsDto.setFirstname("Test");
        userDetailsDto.setLastname("User");

        return userDetailsDto;
    }
}
