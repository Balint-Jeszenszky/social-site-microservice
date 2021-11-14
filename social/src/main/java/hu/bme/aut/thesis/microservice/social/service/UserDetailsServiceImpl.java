package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${social.app.auth-public-details-url}")
    private String publicUserDetailsUrl;

    @Override
    public Optional<UserDetailsDto> getUserDetailsById(Integer userId) {
        try {
            UserDetailsDto userDetailsDto = restTemplateBuilder.build().getForEntity(publicUserDetailsUrl + "/" + userId, UserDetailsDto.class).getBody();
            return Optional.of(userDetailsDto);
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
