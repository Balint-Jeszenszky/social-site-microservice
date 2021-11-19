package hu.bme.aut.thesis.microservice.auth.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static hu.bme.aut.thesis.microservice.auth.model.ERole.ROLE_ADMIN;

@Service
public class LoggedInUserService {

    public UserDetailsImpl getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) authentication.getPrincipal());
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList()).contains(ROLE_ADMIN.name());
    }
}
