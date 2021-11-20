package hu.bme.aut.thesis.microservice.auth.security.jwt;

import hu.bme.aut.thesis.microservice.auth.model.ERole;
import hu.bme.aut.thesis.microservice.auth.model.Role;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${auth.app.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.app.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${auth.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private static String USERNAME = "username";
    private static String FIRSTNAME = "firstname";
    private static String LASTNAME = "lastname";
    private static String EMAIL = "email";
    private static String ROLES = "roles";
    private static String ID = "is";

    public String generateJwtToken(UserDetailsImpl userDetails) {

        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .claim("type", "access")
                .claim(USERNAME, userDetails.getUsername())
                .claim(FIRSTNAME, userDetails.getFirstname())
                .claim(LASTNAME, userDetails.getLastname())
                .claim(EMAIL, userDetails.getEmail())
                .claim(ID, userDetails.getId())
                .claim(ROLES, userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()))
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateJwtRefreshToken(UserDetailsImpl userDetails) {

        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public User getUserFromJwtToken(String token) {
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        String firstname = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(FIRSTNAME, String.class);
        String lastname = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(LASTNAME, String.class);
        String email = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(EMAIL, String.class);
        Integer id = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(ID, Integer.class);
        List<String> roles = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(ROLES, List.class);

        User user = new User(username, firstname, lastname, email, null);
        user.setAcceptedEmail(true);
        user.setId(id);
        user.setRoles(roles.stream().map(r -> new Role(ERole.valueOf(r))).collect(Collectors.toSet()));

        return user;
    }

    public String getUserNameFromJwtRefreshToken(String token) {
        return Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        return validateToken(authToken, jwtSecret);
    }

    public boolean validateJwtRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
