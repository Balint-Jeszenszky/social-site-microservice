package hu.bme.aut.thesis.microservice.social.security.jwt;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${social.app.auth-url}")
    private String authUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", httpServletRequest.getHeader("Authorization").substring(7));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

            ResponseEntity<UserDetailsDto> response = restTemplateBuilder.build().postForEntity(authUrl, entity, UserDetailsDto.class);

            UserDetailsDto userDetailsDto = response.getBody();

            UserDetails userDetails = new UserDetailsImpl(
                    userDetailsDto.getId(),
                    userDetailsDto.getUsername(),
                    userDetailsDto.getFirstname(),
                    userDetailsDto.getLastname(),
                    userDetailsDto.getEmail()
            );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
