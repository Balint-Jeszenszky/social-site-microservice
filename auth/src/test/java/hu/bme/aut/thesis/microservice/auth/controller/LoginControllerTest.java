package hu.bme.aut.thesis.microservice.auth.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.AccessTokenDto;
import hu.bme.aut.thesis.microservice.auth.models.LoginCredentialsDto;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static hu.bme.aut.thesis.microservice.auth.util.TestHelper.asJsonString;
import static hu.bme.aut.thesis.microservice.auth.util.TestHelper.getJsonNode;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_EMAIL = "admin";
    static final String ADMIN_FIRSTNAME = "admin";
    static final String ADMIN_LASTNAME = "admin";
    static final String ADMIN_PASSWORD = "admin";

    static final String USER_NOTREGISTERED = "nouser";

    static final String USER_NOTACCEPTEDEMAIL_USERNAME = "user";
    static final String USER_NOTACCEPTEDEMAIL_PASSWORD = "aaaaaaaaaa";

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User user = new User(
                ADMIN_USERNAME,
                ADMIN_FIRSTNAME,
                ADMIN_LASTNAME,
                ADMIN_EMAIL,
                passwordEncoder.encode(ADMIN_PASSWORD)
        );
        user.setAcceptedEmail(true);
        userRepository.save(user);
    }

    @Test
    void postLoginSuccess() throws Exception {
        LoginCredentialsDto loginCredentialsDto = new LoginCredentialsDto();
        loginCredentialsDto.setUsername(ADMIN_USERNAME);
        loginCredentialsDto.setPassword(ADMIN_PASSWORD);

        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginCredentialsDto));


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDetails.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.userDetails.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.userDetails.firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$.userDetails.lastname").value(ADMIN_LASTNAME))
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.refreshToken").isString());
    }

    @Test
    void postLoginNotRegistered() throws Exception {
        LoginCredentialsDto loginCredentialsDto = new LoginCredentialsDto();
        loginCredentialsDto.setUsername(USER_NOTREGISTERED);
        loginCredentialsDto.setPassword("asdsadasda");

        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginCredentialsDto));


        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void postLoginNotAcceptedEmail() throws Exception {
        LoginCredentialsDto loginCredentialsDto = new LoginCredentialsDto();
        loginCredentialsDto.setUsername(USER_NOTACCEPTEDEMAIL_USERNAME);
        loginCredentialsDto.setPassword(USER_NOTACCEPTEDEMAIL_PASSWORD);

        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginCredentialsDto));


        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void postLoginDetails() throws Exception {
        LoginCredentialsDto loginCredentialsDto = new LoginCredentialsDto();
        loginCredentialsDto.setUsername(ADMIN_USERNAME);
        loginCredentialsDto.setPassword(ADMIN_PASSWORD);

        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginCredentialsDto));

        MvcResult result = mockMvc.perform(request).andReturn();

        String content = result.getResponse().getContentAsString();

        JsonNode accessToken = getJsonNode(content, "/accessToken");

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setAccessToken(accessToken.asText());

        request = MockMvcRequestBuilders.post("/login/details")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(accessTokenDto));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$.lastname").value(ADMIN_LASTNAME));
    }
}