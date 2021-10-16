package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.PostsApi;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.models.PostsDto;
import hu.bme.aut.thesis.microservice.social.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController implements PostsApi {

    @Autowired
    private PostService postService;

    @Override
    public ResponseEntity<PostsDto> getPosts() {
        return new ResponseEntity(postService.getPosts(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostsDto> getPostsUserId(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<NewPostDto> postPosts() {
        return null;
    }
}
