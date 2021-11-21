package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.BadRequestException;
import hu.bme.aut.thesis.microservice.auth.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.auth.model.PasswordReset;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.PasswordResetDto;
import hu.bme.aut.thesis.microservice.auth.repository.PasswordResetRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.service.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SendMail sendMail;

    @Value("${auth.app.baseUrl}")
    private String baseUrl;

    @Transactional
    public void sendResetMail(String email) {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        passwordResetRepository.deleteExpiredKeys();

        if (user.isPresent()) {
            User foundUser = user.get();
            passwordResetRepository.deleteByUserId(foundUser.getId());

            String key = RandomStringGenerator.generate(32);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 30);

            PasswordReset passwordReset = new PasswordReset(foundUser.getId(), calendar.getTime(), key);

            passwordResetRepository.save(passwordReset);

            new Thread(() -> {
                try {
                    sendMail.sendSimpleMessage(foundUser.getEmail(), "New password", baseUrl + "/#/passwordReset?key=" + key + "\nThe link is available for 30 minutes.");
                } catch (MailException e) {
                    passwordResetRepository.deleteById(passwordReset.getId());
                }
            }).start();
        }
    }

    public void resetPassword(PasswordResetDto passwordResetDto) {
        PasswordReset passwordReset = passwordResetRepository.findByKey(passwordResetDto.getKey())
                .orElseThrow(() -> new NotFoundException("Invalid key"));

        if (passwordReset.getExpiration().before(new Date())) {
            passwordResetRepository.delete(passwordReset);
            throw new BadRequestException("Link expired");
        }

        if (!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmPassword())) {
            throw new BadRequestException("Two password not match");
        }

        if (passwordResetDto.getNewPassword().length() < 8) {
            throw new BadRequestException("Password should be at least 8 character");
        }

        Optional<User> user = userRepository.findById(passwordReset.getUserId());

        if (user.isEmpty()) {
            passwordResetRepository.delete(passwordReset);
            throw new NotFoundException("User not found");
        }

        User userToUpdate = user.get();

        userToUpdate.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));

        passwordResetRepository.delete(passwordReset);
    }
}
