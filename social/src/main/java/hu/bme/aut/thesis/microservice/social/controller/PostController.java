package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.PostsApi;
import hu.bme.aut.thesis.microservice.social.mapper.PostMapper;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.models.PostDto;
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
    public ResponseEntity<PostsDto> getPostsUserId(Integer userId) {
        return new ResponseEntity(postService.getPostsByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> postPosts(NewPostDto body) {
        Post post = postService.createPost(body);

        PostDto postDto = PostMapper.INSTANCE.postToPostDto(post);

        return new ResponseEntity(postDto, HttpStatus.OK);
    }
}
