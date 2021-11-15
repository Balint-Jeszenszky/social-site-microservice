package hu.bme.aut.thesis.microservice.auth.service;

public interface SendMail {
    void sendSimpleMessage(String to, String subject, String text);
}
