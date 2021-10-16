package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.PostsApi;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.models.PostsDto;
import org.springframework.http.ResponseEntity;

public class PostController implements PostsApi {
    @Override
    public ResponseEntity<PostsDto> getPosts() {
        return null;
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
