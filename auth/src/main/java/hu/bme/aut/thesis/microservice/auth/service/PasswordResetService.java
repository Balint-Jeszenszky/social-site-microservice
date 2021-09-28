package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendMail sendMail;

    public void resetPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        // TODO
        if (user.isPresent()) {
            User foundUser = user.get();
            new Thread(() -> {
                sendMail.sendSimpleMessage(foundUser.getEmail(), "New password", "pass");
            }).start();
        }
    }
}
