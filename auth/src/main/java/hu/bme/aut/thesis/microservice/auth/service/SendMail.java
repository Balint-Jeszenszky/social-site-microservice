package hu.bme.aut.thesis.microservice.auth.service;

import org.springframework.mail.MailException;

public interface SendMail {
    void sendSimpleMessage(String to, String subject, String text) throws MailException;
}
