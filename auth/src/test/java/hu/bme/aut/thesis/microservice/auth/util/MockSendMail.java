package hu.bme.aut.thesis.microservice.auth.util;

import hu.bme.aut.thesis.microservice.auth.service.SendMail;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MockSendMail implements SendMail {
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

    }
}
