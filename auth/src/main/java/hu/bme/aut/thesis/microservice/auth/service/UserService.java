package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.model.ERole;
import hu.bme.aut.thesis.microservice.auth.model.Role;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.RegisterDto;
import hu.bme.aut.thesis.microservice.auth.models.UpdateUserDto;
import hu.bme.aut.thesis.microservice.auth.repository.RoleRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.security.service.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoggedInUserService loggedInUserService;

    public void deleteUserById(Integer id) {
        if (!loggedInUserService.getLoggedInUser().getId().equals(id) && !loggedInUserService.getLoggedInUser().getAuthorities().contains(ERole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("Wrong userId");
        }

        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }

        userRepository.deleteById(id);
    }

    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || !user.get().isAcceptedEmail()) {
            throw new NoSuchElementException("User not found");
        }

        return user.get();
    }

    public User editUser(Integer id, UpdateUserDto updateUserDto) {
        if (!loggedInUserService.getLoggedInUser().getId().equals(id) && !loggedInUserService.getLoggedInUser().getAuthorities().contains(ERole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("Wrong userId");
        }

        validateEmail(updateUserDto.getEmail());

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || !user.get().isAcceptedEmail()) {
            throw new NoSuchElementException("User not found");
        }

        User userToUpdate = user.get();

        if (updateUserDto.getOldpassword() != null && !updateUserDto.getOldpassword().isEmpty()) {
            if (!updateUserDto.getNewpassword().equals(updateUserDto.getConfirmpassword())) {
                throw new IllegalArgumentException("Passwords not match");
            }

            String encodedOldPassword = passwordEncoder.encode(updateUserDto.getOldpassword());

            if (!userToUpdate.getPassword().equals(encodedOldPassword)) {
                throw new IllegalArgumentException("Wrong password");
            }
        }

        if (!userToUpdate.getEmail().equals(updateUserDto.getEmail())) {
            emailVerificationService.sendVerificationEmail(userToUpdate);
        }

        userToUpdate.setFirstname(updateUserDto.getFirstname());
        userToUpdate.setLastname(updateUserDto.getLastname());

        userRepository.save(userToUpdate);

        return userToUpdate;
    }

    public User registerUser(RegisterDto registerDto) {
        validateEmail(registerDto.getEmail());

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Error: Email is already in use!");
        }

        Pattern usernamePattern = Pattern.compile("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
        Matcher usernameMatcher = usernamePattern.matcher(registerDto.getUsername());
        if (!usernameMatcher.matches()) {
            throw new IllegalArgumentException("Error: Username should be 4-20 character of letters, . or _ with no double . or _!");
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Error: Username is already taken!");
        }

        if (registerDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Error: Password should be at least 8 character!");
        }

        User user = new User(
                registerDto.getUsername(),
                registerDto.getFirstname(),
                registerDto.getLastname(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword())
        );

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        emailVerificationService.sendVerificationEmail(user);

        return user;
    }


    private void validateEmail(String email) {
        Pattern emailPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches()) {
            throw new IllegalArgumentException("Error: Email is invalid!");
        }
    }
}
