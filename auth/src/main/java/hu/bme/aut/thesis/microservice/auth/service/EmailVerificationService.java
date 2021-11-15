package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.auth.model.EmailVerification;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.repository.EmailVerificationRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.service.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private SendMail sendMail;

    @Value("${auth.app.baseUrl}")
    private String baseUrl;

    public void validate(String key) {
        EmailVerification emailVerification = emailVerificationRepository.getEmailVerificationByKey(key)
                .orElseThrow(() -> new NotFoundException("Email not found"));

        User user = userRepository.getById(emailVerification.getUserId());

        user.setEmail(emailVerification.getEmail());
        user.setAcceptedEmail(true);

        emailVerificationRepository.delete(emailVerification);
    }

    public void sendVerificationEmail(Integer userId, String email) {
        String key = RandomStringGenerator.generate(32);

        EmailVerification emailVerification = new EmailVerification(
                email,
                userId,
                key
        );

        emailVerificationRepository.save(emailVerification);

        sendMail.sendSimpleMessage(email, "Registration", baseUrl + "/#/validate/" + key);
    }
}
