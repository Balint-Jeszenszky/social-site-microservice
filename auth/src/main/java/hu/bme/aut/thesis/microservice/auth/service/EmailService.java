package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.model.EmailVerification;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.repository.EmailVerificationRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;

@Service
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    public void validate(String key) {
        EmailVerification emailVerification = emailVerificationRepository.getEmailVerificationByKey(key).orElseThrow(() -> new NoSuchElementException("Email not found"));

        User user = userRepository.getById(emailVerification.getUserId());

        user.setEmail(emailVerification.getEmail());
        user.setAcceptedEmail(true);

        emailVerificationRepository.delete(emailVerification);
    }

    public void sendVerificationEmail(User user) {
        String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }

        String key = secureRandom.ints(32, 0, chrs.length())
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        EmailVerification emailVerification = new EmailVerification(
                user.getEmail(),
                user.getId(),
                key
        );

        // TODO send mail
    }
}
