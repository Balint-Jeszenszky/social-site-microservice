package hu.bme.aut.thesis.microservice.auth.util;

import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.security.jwt.JwtUtils;
import hu.bme.aut.thesis.microservice.auth.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    @Autowired
    private JwtUtils jwtUtils;

    public String getAccessToken(User user) {
        return jwtUtils.generateJwtToken(UserDetailsImpl.build(user));
    }

}
