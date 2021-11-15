package hu.bme.aut.thesis.microservice.auth.controller;

import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.UpdateUserDto;
import hu.bme.aut.thesis.microservice.auth.repository.EmailVerificationRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.util.AuthHelper;
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

import static hu.bme.aut.thesis.microservice.auth.util.TestHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailVerificationRepository emailVerificationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthHelper authHelper;

    final String ADMIN_USERNAME = "admin";
    final String ADMIN_EMAIL = "admin@asd.asd";
    final String ADMIN_FIRSTNAME = "Someone";
    final String ADMIN_LASTNAME = "Anyone";
    final String ADMIN_PASSWORD = "admin";

    String accessToken;

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

        accessToken = authHelper.getAccessToken(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        emailVerificationRepository.deleteAll();
    }

    @Test
    void deleteUserId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    void deleteUserIdOtherUser() throws Exception {
        User otherUser = new User(
                "user",
                "asd",
                "asd",
                "asd@asd.asd",
                "asd"
        );

        otherUser.setAcceptedEmail(true);
        userRepository.save(otherUser);

        String otherUserAccessToken = authHelper.getAccessToken(otherUser);

        RequestBuilder request = MockMvcRequestBuilders.delete("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + otherUserAccessToken);

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    void getUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken);

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(user.getLastname()));
    }

    @Test
    void getUserOtherUser() throws Exception {
        User otherUser = new User(
                "user",
                "asd",
                "asd",
                "asd@asd.asd",
                passwordEncoder.encode("asdasdasd")
        );

        otherUser.setAcceptedEmail(true);
        userRepository.save(otherUser);

        String otherUserAccessToken = authHelper.getAccessToken(otherUser);

        RequestBuilder request = MockMvcRequestBuilders.get("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + otherUserAccessToken);

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    void putUserId() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail(ADMIN_EMAIL);
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + accessToken)
            .content(asJsonString(updateUserDto));

        mockMvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstname").value("newFirstName"))
                .andExpect(jsonPath("$.lastname").value("newLastName"));

        assertTrue(userRepository.findById(user.getId()).get().isAcceptedEmail());
        assertTrue(emailVerificationRepository.findAll().isEmpty());
        assertTrue(passwordEncoder.matches(ADMIN_PASSWORD, userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    void putUserIdWithPassword() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail(ADMIN_EMAIL);
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");
        updateUserDto.setOldpassword(ADMIN_PASSWORD);
        updateUserDto.setNewpassword("asdasdasd");
        updateUserDto.setConfirmpassword("asdasdasd");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(updateUserDto));

        mockMvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstname").value("newFirstName"))
                .andExpect(jsonPath("$.lastname").value("newLastName"));

        assertTrue(passwordEncoder.matches("asdasdasd", userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    void putUserIdWithWrongPassword() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail(ADMIN_EMAIL);
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");
        updateUserDto.setOldpassword("wrong_pass");
        updateUserDto.setNewpassword("asdasdasd");
        updateUserDto.setConfirmpassword("asdasdasd");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(updateUserDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void putUserIdWithNotMatchingPassword() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail(ADMIN_EMAIL);
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");
        updateUserDto.setOldpassword(ADMIN_PASSWORD);
        updateUserDto.setNewpassword("asdasdasd");
        updateUserDto.setConfirmpassword("not_matching");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(updateUserDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void putUserIdWithNewEmail() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("asd@asd.asd");
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(updateUserDto));

        mockMvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(ADMIN_USERNAME))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstname").value("newFirstName"))
                .andExpect(jsonPath("$.lastname").value("newLastName"));

        assertTrue(userRepository.findById(user.getId()).get().isAcceptedEmail());
        assertEquals(emailVerificationRepository.findAll().get(0).getUserId(), user.getId());
        assertEquals(emailVerificationRepository.findAll().get(0).getEmail(), "asd@asd.asd");
        assertEquals(userRepository.findById(user.getId()).get().getEmail(), ADMIN_EMAIL);
    }

    @Test
    void putUserIdWithWrongEmail() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("notAnEmail");
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");
        updateUserDto.setOldpassword(ADMIN_PASSWORD);
        updateUserDto.setNewpassword("asdasdasd");
        updateUserDto.setConfirmpassword("not_matching");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(updateUserDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void putUserIdWithRegisteredEmail() throws Exception {
        User otherUser = new User(
                "user",
                "asd",
                "asd",
                "asd@asd.asd",
                "asd"
        );

        user.setAcceptedEmail(true);
        userRepository.save(otherUser);

        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("notAnEmail");
        updateUserDto.setFirstname("newFirstName");
        updateUserDto.setLastname("newLastName");
        updateUserDto.setOldpassword(ADMIN_PASSWORD);
        updateUserDto.setNewpassword("asdasdasd");
        updateUserDto.setConfirmpassword("not_matching");

        RequestBuilder request = MockMvcRequestBuilders.put("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(updateUserDto));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}