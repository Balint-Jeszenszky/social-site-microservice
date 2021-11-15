package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.util.AuthHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PublicUserDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthHelper authHelper;

    final String ADMIN_USERNAME = "admin";
    final String ADMIN_EMAIL = "admin@asd.asd";
    final String ADMIN_FIRSTNAME = "Someone";
    final String ADMIN_LASTNAME = "Anyone";

    String accessToken;

    User user;

    @BeforeEach
    void setUp() {
        user = new User(
                ADMIN_USERNAME,
                ADMIN_FIRSTNAME,
                ADMIN_LASTNAME,
                ADMIN_EMAIL,
                "asdasdasd"
        );

        user.setAcceptedEmail(true);
        userRepository.save(user);

        accessToken = authHelper.getAccessToken(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getPublicUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/publicUser/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$.lastname").value(ADMIN_LASTNAME));
    }

    @Test
    void getPublicUserFindUsername() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/publicUser/find/" + ADMIN_USERNAME)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$.lastname").value(ADMIN_LASTNAME));
    }

    @Test
    void getPublicUserSearchQueryByUsernamePart() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/publicUser/search/" + ADMIN_USERNAME.substring(1, 4))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$[0].email").doesNotExist())
                .andExpect(jsonPath("$[0].firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$[0].lastname").value(ADMIN_LASTNAME));
    }


    @Test
    void getPublicUserSearchQueryByFirstnamePart() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/publicUser/search/" + ADMIN_FIRSTNAME.substring(2, 5))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$[0].email").doesNotExist())
                .andExpect(jsonPath("$[0].firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$[0].lastname").value(ADMIN_LASTNAME));
    }


    @Test
    void getPublicUserSearchQueryByLastnamePart() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/publicUser/search/" + ADMIN_LASTNAME.substring(2, 5))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$[0].email").doesNotExist())
                .andExpect(jsonPath("$[0].firstname").value(ADMIN_FIRSTNAME))
                .andExpect(jsonPath("$[0].lastname").value(ADMIN_LASTNAME));
    }
}