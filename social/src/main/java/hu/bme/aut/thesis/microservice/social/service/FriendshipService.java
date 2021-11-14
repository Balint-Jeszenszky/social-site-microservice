package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.BadRequestException;
import hu.bme.aut.thesis.microservice.social.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.social.model.Friendship;
import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.repository.FriendshipRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    @Autowired
    private LoggedInUserService loggedInUserService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserDetailsService userDetailsService;


    public void deleteFriend(Integer id) {
        Integer loggedInUserId = loggedInUserService.getLoggedInUser().getId();
        Optional<Friendship> friendship = friendshipRepository.findByUserId(loggedInUserId, id);

        if (friendship.isPresent()) {
            friendshipRepository.delete(friendship.get());
        }
    }

    public void addFriend(Integer id) {
        Integer loggedInUserId = loggedInUserService.getLoggedInUser().getId();

        Optional<Friendship> existingFriendship = friendshipRepository.findByUserId(loggedInUserId, id);

        if (existingFriendship.isPresent()) {
            Friendship friendship = existingFriendship.get();

            if (!friendship.getPending()) {
                throw new BadRequestException("Already friends");
            }

            if (friendship.getFirstUserId().equals(loggedInUserId)) {
                throw new BadRequestException("Request already sent");
            }
        }

        Optional<Friendship> friendRequestResult = friendshipRepository.findFriendRequestByUserId(id, loggedInUserId);

        if (friendRequestResult.isEmpty()) {
            Optional<UserDetailsDto> userDetails = userDetailsService.getUserDetailsById(id);

            if (userDetails.isEmpty()) {
                throw new NotFoundException("User not found");
            }

            Friendship friendship = new Friendship();
            friendship.setFirstUserId(loggedInUserId);
            friendship.setSecondUserId(id);
            friendshipRepository.save(friendship);
            return;
        }

        Friendship friendship = friendRequestResult.get();
        friendship.setPending(false);
        friendshipRepository.save(friendship);
    }

    public List<UserDetailsDto> getFriendListForUser(Integer userId) {
        List<Integer> friendIds = friendshipRepository.findUserFriendsById(userId);
        return friendIds.stream()
                .map(i -> userDetailsService.getUserDetailsById(i).orElse(null))
                .filter(f -> f != null).collect(Collectors.toList());
    }

    public List<UserDetailsDto> getPendingRequests() {
        Integer loggedInUserId = loggedInUserService.getLoggedInUser().getId();
        List<Friendship> pendingRequests = friendshipRepository.findPendingForUser(loggedInUserId);
        return pendingRequests.stream().map(r -> {
            Integer userId = (loggedInUserId.equals(r.getFirstUserId())) ? r.getSecondUserId() : r.getFirstUserId();
            return userDetailsService.getUserDetailsById(userId).orElse(null);
        }).filter(f -> f != null).collect(Collectors.toList());
    }

    public List<Integer> getFriendIdsForUser(Integer userId) {
        return friendshipRepository.findUserFriendsById(userId);
    }

    public boolean areFriends(Integer firstUserId, Integer secondUserId) {
        Optional<Friendship> friendship = friendshipRepository.findAcceptedByUserId(firstUserId, secondUserId);

        return friendship.isPresent();
    }

    public void deleteAllFriendsForCurrentUser() {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        List<Friendship> friendships = friendshipRepository.findAllByUserId(userId);

        //friendshipRepository.deleteAll(friendships);

        // TODO @Transactional not working for some reason, fix it!
        for (Friendship friendship : friendships) {
            friendshipRepository.delete(friendship);
        }
    }
}
