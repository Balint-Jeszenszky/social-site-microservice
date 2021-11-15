package hu.bme.aut.thesis.microservice.auth.service.util;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.InternalServerErrorException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomStringGenerator {
    public static String generate(Integer length) {
        String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        return secureRandom.ints(length, 0, chrs.length())
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
}
