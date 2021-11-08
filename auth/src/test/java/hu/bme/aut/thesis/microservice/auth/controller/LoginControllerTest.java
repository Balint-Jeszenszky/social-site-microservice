package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.LoginCredentialsDto;
import hu.bme.aut.thesis.microservice.auth.models.LoginDetailsDto;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoginControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User user = new User(
                "admin",
                "Admin",
                "Adin",
                "mail@example.com",
                passwordEncoder.encode("asdasdasd")
        );
        userRepository.save(user);
    }

    @Test
    void postLogin() throws Exception {
//        LoginCredentialsDto loginCredentialsDto = new LoginCredentialsDto();
//        loginCredentialsDto.setUsername("admin");
//        loginCredentialsDto.setPassword("asdasdasd");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity(loginCredentialsDto, headers);
//
//        ResponseEntity<LoginDetailsDto> result = restTemplate.postForEntity("http://localhost:" + port + "/api/auth/login", entity, LoginDetailsDto.class);
//        assertEquals(result.getBody().getUserDetails().getUsername(), "admin");
//        assertEquals(result.getBody().getUserDetails().getEmail(), "mail@example.com");
//        assertEquals(result.getBody().getUserDetails().getFirstname(), "Admin");
//        assertEquals(result.getBody().getUserDetails().getLastname(), "Admin");
//        assertNotNull(result.getBody().getAccessToken());
//        assertNotNull(result.getBody().getRefreshToken());
    }

    @Test
    void postLoginDetails() {
    }
}