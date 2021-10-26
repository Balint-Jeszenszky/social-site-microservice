package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.FriendApi;
import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendshipController implements FriendApi {

    @Autowired
    private FriendshipService friendshipService;

    @Override
    public ResponseEntity<Void> deleteFriendId(Integer id) {
        friendshipService.deleteFriend(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<UserDetailsDto>> getFriendListUserId(Integer userId) {
        List<UserDetailsDto> friendListForUser = friendshipService.getFriendListForUser(userId);

        return new ResponseEntity(friendListForUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDetailsDto>> getFriendRequests() {
        List<UserDetailsDto> pendingRequests = friendshipService.getPendingRequests();

        return new ResponseEntity(pendingRequests, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postFriendId(Integer id) {
        friendshipService.addFriend(id);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
