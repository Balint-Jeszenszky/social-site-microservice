package hu.bme.aut.thesis.microservice.auth.service;

import hu.bme.aut.thesis.microservice.auth.controller.exceptions.BadRequestException;
import hu.bme.aut.thesis.microservice.auth.controller.exceptions.ForbiddenException;
import hu.bme.aut.thesis.microservice.auth.controller.exceptions.InternalServerErrorException;
import hu.bme.aut.thesis.microservice.auth.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.auth.model.ERole;
import hu.bme.aut.thesis.microservice.auth.model.Role;
import hu.bme.aut.thesis.microservice.auth.model.User;
import hu.bme.aut.thesis.microservice.auth.models.RegisterDto;
import hu.bme.aut.thesis.microservice.auth.models.UpdateUserDto;
import hu.bme.aut.thesis.microservice.auth.repository.RoleRepository;
import hu.bme.aut.thesis.microservice.auth.repository.UserRepository;
import hu.bme.aut.thesis.microservice.auth.security.service.LoggedInUserService;
import hu.bme.aut.thesis.microservice.auth.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        if (!userId.equals(id) && !loggedInUserService.isAdmin()) {
            throw new ForbiddenException("Wrong userId");
        }

        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

    public User getUserById(Integer id) {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        if (!userId.equals(id) && !loggedInUserService.isAdmin()) {
            throw new ForbiddenException("Wrong userId");
        }

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || !user.get().isAcceptedEmail()) {
            throw new NotFoundException("User not found");
        }

        return user.get();
    }

    public User getPublicUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || !user.get().isAcceptedEmail()) {
            throw new NotFoundException("User not found");
        }

        User publicUser = user.get();
        publicUser.setEmail("");
        publicUser.setPassword("");

        return publicUser;
    }

    public User editUser(Integer id, UpdateUserDto updateUserDto) {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        if (!userId.equals(id) && !loggedInUserService.isAdmin()) {
            throw new ForbiddenException("Wrong userId");
        }

        validateEmail(updateUserDto.getEmail());

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || !user.get().isAcceptedEmail()) {
            throw new NotFoundException("User not found");
        }

        User userToUpdate = user.get();

        if (!userToUpdate.getEmail().equals(updateUserDto.getEmail()) && userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        if (updateUserDto.getOldpassword() != null && !updateUserDto.getOldpassword().isEmpty()) {

            if (!passwordEncoder.matches(updateUserDto.getOldpassword(), userToUpdate.getPassword())) {
                throw new BadRequestException("Wrong password");
            }

            if (!updateUserDto.getNewpassword().equals(updateUserDto.getConfirmpassword())) {
                throw new BadRequestException("Passwords not match");
            }

            if (updateUserDto.getNewpassword().length() < 8) {
                throw new BadRequestException("Passwords too sort");
            }

            String encodedNewPassword = passwordEncoder.encode(updateUserDto.getNewpassword());

            userToUpdate.setPassword(encodedNewPassword);
        }

        if (!userToUpdate.getEmail().equals(updateUserDto.getEmail())) {
            emailVerificationService.sendVerificationEmail(userToUpdate.getId(), updateUserDto.getEmail());
        }

        userToUpdate.setFirstname(updateUserDto.getFirstname());
        userToUpdate.setLastname(updateUserDto.getLastname());

        userRepository.save(userToUpdate);

        return userToUpdate;
    }

    public User registerUser(RegisterDto registerDto) {
        validateEmail(registerDto.getEmail());

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        String username = registerDto.getUsername().toLowerCase();

        Pattern usernamePattern = Pattern.compile("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
        Matcher usernameMatcher = usernamePattern.matcher(username);
        if (!usernameMatcher.matches()) {
            throw new BadRequestException("Error: Username should be 4-20 character of letters, . or _ with no double . or _!");
        }

        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Error: Username is already taken!");
        }

        if (registerDto.getPassword().length() < 8) {
            throw new BadRequestException("Error: Password should be at least 8 character!");
        }

        User user = new User(
                username,
                registerDto.getFirstname(),
                registerDto.getLastname(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword())
        );

        Set<Role> roles = new HashSet<>();

        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);

        if (role.isEmpty()) {
            createDefaultRoles();
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new InternalServerErrorException("Error: Role is not found.")));
        }

        roles.add(roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new InternalServerErrorException("Error: Role is not found.")));

        user.setRoles(roles);

        userRepository.save(user);

        emailVerificationService.sendVerificationEmail(user.getId(), user.getEmail());

        return user;
    }

    private void createDefaultRoles() {
        roleRepository.save(new Role(ERole.ROLE_ADMIN));
        roleRepository.save(new Role(ERole.ROLE_USER));
    }

    private void validateEmail(String email) {
        Pattern emailPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches()) {
            throw new BadRequestException("Error: Email is invalid!");
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return user.get();
    }

    public List<User> searchUsers(String query) {
        if (query.length() < 3) {
            return Collections.emptyList();
        }
        return userRepository.searchUser(query);
    }

    public void updateProfilePicture(Integer id, String name) {
        Integer loggedInUserId = loggedInUserService.getLoggedInUser().getId();

        if (!loggedInUserId.equals(id) && !loggedInUserService.isAdmin()) {
            throw new ForbiddenException("Wrong user");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.setProfilePicture(name);

        userRepository.save(user);
    }
}
