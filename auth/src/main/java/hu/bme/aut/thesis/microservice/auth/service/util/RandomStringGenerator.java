package hu.bme.aut.thesis.microservice.auth.service.util;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.InternalServerErrorException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomStringGenerator {
    private static SecureRandom secureRandom;

    public static String generate(Integer length) {
        String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (secureRandom == null) {
            try {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                throw new InternalServerErrorException(e.getMessage());
            }
        }

        return secureRandom.ints(length, 0, chrs.length())
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
}
