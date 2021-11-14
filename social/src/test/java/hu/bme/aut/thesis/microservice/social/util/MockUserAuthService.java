package hu.bme.aut.thesis.microservice.social.util;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.security.UserAuthService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MockUserAuthService implements UserAuthService {

    private static UserDetailsDto userDetailsDto;

    public MockUserAuthService() {
        userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(1);
        userDetailsDto.setEmail("asd@asd.asd");
        userDetailsDto.setUsername("user");
        userDetailsDto.setFirstname("Test");
        userDetailsDto.setLastname("User");
    }

    @Override
    public UserDetailsDto checkAuth(String accessToken) {
        if (!accessToken.equals("test")) {
            return null;
        }

        return userDetailsDto;
    }

    public static UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public static void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        MockUserAuthService.userDetailsDto = userDetailsDto;
    }
}
