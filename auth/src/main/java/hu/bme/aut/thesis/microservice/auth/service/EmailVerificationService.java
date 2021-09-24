package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.model.EmailVerification;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.repository.EmailVerificationRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;

@Service
public class EmailVerificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${auth.app.email}")
    private String email;

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

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
        emailVerificationRepository.save(emailVerification);

        sendSimpleMessage(user.getEmail(), "Registration", key);
    }
}
