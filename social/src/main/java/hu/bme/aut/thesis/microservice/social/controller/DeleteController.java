package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.DeleteApi;
import hu.bme.aut.thesis.microservice.social.service.FriendshipService;
import hu.bme.aut.thesis.microservice.social.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteController implements DeleteApi {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private PostService postService;

    @Override
    public ResponseEntity<Void> deleteDeleteUserId() {
        friendshipService.deleteAllFriendsForCurrentUser();
        postService.deleteAllPostsForCurrentUser();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
