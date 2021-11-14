package hu.bme.aut.thesis.microservice.social.util;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.service.UserDetailsService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class MockUserDetailsService implements UserDetailsService {

    @Override
    public Optional<UserDetailsDto> getUserDetailsById(Integer userId) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(userId);
        userDetailsDto.setFirstname("No" + userId);
        userDetailsDto.setLastname("User");
        userDetailsDto.setUsername("user" + userId);
        userDetailsDto.setEmail("user" + userId + "@asd.asd");

        return Optional.of(userDetailsDto);
    }
}
