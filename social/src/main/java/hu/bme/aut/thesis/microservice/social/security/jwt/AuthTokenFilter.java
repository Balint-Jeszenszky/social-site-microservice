package hu.bme.aut.thesis.microservice.social.security.jwt;

import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.security.UserAuthService;
import hu.bme.aut.thesis.microservice.social.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = httpServletRequest.getHeader("Authorization").substring(7);

            UserDetailsDto userDetailsDto = userAuthService.checkAuth(accessToken);

            UserDetails userDetails = new UserDetailsImpl(
                    userDetailsDto.getId(),
                    userDetailsDto.getUsername(),
                    userDetailsDto.getFirstname(),
                    userDetailsDto.getLastname(),
                    userDetailsDto.getEmail(),
                    userDetailsDto.getRoles()
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
