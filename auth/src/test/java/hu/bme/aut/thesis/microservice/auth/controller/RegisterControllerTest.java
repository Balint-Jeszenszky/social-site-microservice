package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.model.EmailVerification;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.RegisterDto;
import hu.bme.aut.thesis.microservice.auth.repository.EmailVerificationRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
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

import static hu.bme.aut.thesis.microservice.auth.util.TestHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailVerificationRepository emailVerificationRepository;

    final String ADMIN_USERNAME = "admin";
    final String ADMIN_EMAIL = "admin";
    final String ADMIN_FIRSTNAME = "admin";
    final String ADMIN_LASTNAME = "admin";

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

        user.setAcceptedEmail(false);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        emailVerificationRepository.deleteAll();
    }

    @Test
    void getRegisterValidate() throws Exception {
        EmailVerification emailVerification = new EmailVerification("asd@asd.asd", user.getId(), "valid-key");
        emailVerificationRepository.save(emailVerification);

        RequestBuilder request = MockMvcRequestBuilders.get("/register/validate?key=valid-key")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isAccepted());

        assertTrue(userRepository.findById(user.getId()).get().isAcceptedEmail());
    }

    @Test
    void getRegisterValidateNonExistingKey() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/register/validate?key=invalid-key")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    void postRegister() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("asd@asd.asd");
        registerDto.setUsername("user");
        registerDto.setFirstname("asd");
        registerDto.setLastname("asd");
        registerDto.setPassword("asdasdasd");
        registerDto.setConfirmPassword("asdasdasd");

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        mockMvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("asd@asd.asd"))
                .andExpect(jsonPath("$.firstname").value("asd"))
                .andExpect(jsonPath("$.lastname").value("asd"));
    }

    @Test
    void postRegisterExistingUsername() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("asd@asd.asd");
        registerDto.setUsername(ADMIN_USERNAME);
        registerDto.setFirstname("asd");
        registerDto.setLastname("asd");
        registerDto.setPassword("asdasdasd");
        registerDto.setConfirmPassword("asdasdasd");

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void postRegisterExistingEmail() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail(ADMIN_EMAIL);
        registerDto.setUsername("asdasdas");
        registerDto.setFirstname("asd");
        registerDto.setLastname("asd");
        registerDto.setPassword("asdasdasd");
        registerDto.setConfirmPassword("asdasdasd");

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void postRegisterInvalidEmail() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("asdasda");
        registerDto.setUsername("asdasdas");
        registerDto.setFirstname("asd");
        registerDto.setLastname("asd");
        registerDto.setPassword("asdasdasd");
        registerDto.setConfirmPassword("asdasdasd");

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void postRegisterNotMatchingPassword() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("asdasda");
        registerDto.setUsername("asdasdas");
        registerDto.setFirstname("asd");
        registerDto.setLastname("asd");
        registerDto.setPassword("asdasdasd");
        registerDto.setConfirmPassword("asdasdas");

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void postRegisterWeakPassword() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("asdasda");
        registerDto.setUsername("asdasdas");
        registerDto.setFirstname("asd");
        registerDto.setLastname("asd");
        registerDto.setPassword("a");
        registerDto.setConfirmPassword("a");

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}