package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${social.app.auth-public-details-url}")
    private String publicUserDetailsUrl;

    public UserDetailsDto getUserDetailsById(Integer userId) {
        return restTemplateBuilder.build().getForEntity(publicUserDetailsUrl + "/" + userId, UserDetailsDto.class).getBody();
    }
}
