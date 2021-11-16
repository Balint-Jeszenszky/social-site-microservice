package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.model.PasswordReset;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.ForgotPasswordDto;
import hu.bme.aut.thesis.microservice.auth.models.PasswordResetDto;
import hu.bme.aut.thesis.microservice.auth.repository.PasswordResetRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;

import static hu.bme.aut.thesis.microservice.auth.util.TestHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PasswordResetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetRepository passwordResetRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    final String ADMIN_USERNAME = "admin";
    final String ADMIN_EMAIL = "admin";
    final String ADMIN_FIRSTNAME = "admin";
    final String ADMIN_LASTNAME = "admin";
    final String ADMIN_PASSWORD = "admin";

    User user;

    @BeforeEach
    void setUp() {
        user = new User(
                ADMIN_USERNAME,
                ADMIN_FIRSTNAME,
                ADMIN_LASTNAME,
                ADMIN_EMAIL,
                passwordEncoder.encode(ADMIN_PASSWORD)
        );
        user.setAcceptedEmail(true);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        passwordResetRepository.deleteAll();
    }

    @Test
    void postForgotPassword() throws Exception {
        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail(ADMIN_EMAIL);

        RequestBuilder request = MockMvcRequestBuilders.post("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(forgotPasswordDto));


        mockMvc.perform(request).andExpect(status().isAccepted());

        assertEquals(1, passwordResetRepository.findAll().size());
    }

    @Test
    void postForgotPasswordNotExisting() throws Exception {
        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail("notexisting@asd.asd");

        RequestBuilder request = MockMvcRequestBuilders.post("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(forgotPasswordDto));


        mockMvc.perform(request).andExpect(status().isAccepted());

        assertTrue(passwordResetRepository.findAll().isEmpty());
    }

    @Test
    void putForgotPassword() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        PasswordReset passwordReset = new PasswordReset(user.getId(), calendar.getTime(), "reset-key");

        passwordResetRepository.save(passwordReset);

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setNewPassword("asdasdasd");
        passwordResetDto.setConfirmPassword("asdasdasd");
        passwordResetDto.setKey("reset-key");

        RequestBuilder request = MockMvcRequestBuilders.put("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(passwordResetDto));

        mockMvc.perform(request).andExpect(status().isAccepted());

        assertTrue(passwordResetRepository.findAll().isEmpty());
        assertTrue(passwordEncoder.matches("asdasdasd", userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    void putForgotPasswordInvalidKey() throws Exception {
        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setNewPassword("asdasdasd");
        passwordResetDto.setConfirmPassword("asdasdasd");
        passwordResetDto.setKey("invalid-key");

        RequestBuilder request = MockMvcRequestBuilders.put("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(passwordResetDto));

        mockMvc.perform(request).andExpect(status().isNotFound());

        assertTrue(passwordResetRepository.findAll().isEmpty());
        assertTrue(passwordEncoder.matches(ADMIN_PASSWORD, userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    void putForgotPasswordExpired() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -2);
        PasswordReset passwordReset = new PasswordReset(user.getId(), calendar.getTime(), "reset-key");

        passwordResetRepository.save(passwordReset);

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setNewPassword("asdasdasd");
        passwordResetDto.setConfirmPassword("asdasdasd");
        passwordResetDto.setKey("reset-key");

        RequestBuilder request = MockMvcRequestBuilders.put("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(passwordResetDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());

        assertTrue(passwordResetRepository.findAll().isEmpty());
        assertTrue(passwordEncoder.matches(ADMIN_PASSWORD, userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    void putForgotPasswordNotMatching() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        PasswordReset passwordReset = new PasswordReset(user.getId(), calendar.getTime(), "reset-key");

        passwordResetRepository.save(passwordReset);

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setNewPassword("asdasdasd");
        passwordResetDto.setConfirmPassword("asdasdas");
        passwordResetDto.setKey("reset-key");

        RequestBuilder request = MockMvcRequestBuilders.put("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(passwordResetDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());

        assertEquals(1, passwordResetRepository.findAll().size());
        assertTrue(passwordEncoder.matches(ADMIN_PASSWORD, userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    void putForgotPasswordWeakPassword() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        PasswordReset passwordReset = new PasswordReset(user.getId(), calendar.getTime(), "reset-key");

        passwordResetRepository.save(passwordReset);

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setNewPassword("asdas");
        passwordResetDto.setConfirmPassword("asdas");
        passwordResetDto.setKey("reset-key");

        RequestBuilder request = MockMvcRequestBuilders.put("/passwordReset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(passwordResetDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());

        assertEquals(1, passwordResetRepository.findAll().size());
        assertTrue(passwordEncoder.matches(ADMIN_PASSWORD, userRepository.findById(user.getId()).get().getPassword()));
    }
}