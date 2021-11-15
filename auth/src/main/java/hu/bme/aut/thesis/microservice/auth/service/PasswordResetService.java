package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.BadRequestException;
import hu.bme.aut.thesis.microservice.auth.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.auth.model.ForgotPassword;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.PasswordResetDto;
import hu.bme.aut.thesis.microservice.auth.repository.ForgotPasswordRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.service.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SendMail sendMail;

    @Value("${auth.app.baseUrl}")
    private String baseUrl;

    public void sendResetMail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {

            new Thread(() -> {
                User foundUser = user.get();

                String key = RandomStringGenerator.generate(32);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 1);

                ForgotPassword forgotPassword = new ForgotPassword(foundUser.getId(), calendar.getTime(), key);

                forgotPasswordRepository.save(forgotPassword);

                sendMail.sendSimpleMessage(foundUser.getEmail(), "New password", baseUrl + "/#/resetPassword?key=" + key);
            }).start();
        }
    }

    public void resetPassword(PasswordResetDto passwordResetDto) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByKey(passwordResetDto.getKey())
                .orElseThrow(() -> new NotFoundException("Invalid key"));

        if (!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmPassword())) {
            throw new BadRequestException("Two password not match");
        }

        if (passwordResetDto.getNewPassword().length() < 8) {
            throw new BadRequestException("Password should be at least 8 character");
        }

        Optional<User> user = userRepository.findById(forgotPassword.getUserId());

        if (user.isEmpty()) {
            forgotPasswordRepository.delete(forgotPassword);
            throw new NotFoundException("User not found");
        }

        User userToUpdate = user.get();

        userToUpdate.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));

        forgotPasswordRepository.delete(forgotPassword);
    }
}
