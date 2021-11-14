package hu.bme.aut.thesis.microservice.social.security;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${social.app.auth-login-url}")
    private String authUrl;

    @Override
    public UserDetailsDto checkAuth(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<UserDetailsDto> response = restTemplateBuilder.build().postForEntity(authUrl, entity, UserDetailsDto.class);

        return response.getBody();
    }
}
