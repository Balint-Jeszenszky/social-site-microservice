package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.LikeApi;
import hu.bme.aut.thesis.microservice.social.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController implements LikeApi {

    @Autowired
    private LikeService likeService;

    @Override
    public ResponseEntity<Void> deleteLikePostId(Integer postId) {
        likeService.unlikePost(postId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> postLikePostId(Integer postId) {
        likeService.likePost(postId);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
