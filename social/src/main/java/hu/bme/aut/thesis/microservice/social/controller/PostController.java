package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.PostApi;
import hu.bme.aut.thesis.microservice.social.mapper.PostMapper;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.models.PostDto;
import hu.bme.aut.thesis.microservice.social.service.LikeService;
import hu.bme.aut.thesis.microservice.social.service.PostService;
import hu.bme.aut.thesis.microservice.social.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController implements PostApi {

    @Autowired
    private PostService postService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LikeService likeService;

    @Override
    public ResponseEntity<Void> deletePostPostId(Integer postId) {
        postService.deletePost(postId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<PostDto>> getPostAll() {
        return new ResponseEntity(mapUsersAndLikesToPosts(postService.getPosts()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PostDto>> getPostAllUserId(Integer userId) {
        return new ResponseEntity(mapUsersAndLikesToPosts(postService.getPostsByUserId(userId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> postPost(NewPostDto body) {
        Post post = postService.createPost(body);

        PostDto postDto = PostMapper.INSTANCE.postToPostDto(post);

        postDto.setUser(userDetailsService.getUserDetailsById(post.getUserId()).get());

        return new ResponseEntity(postDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PostDto> putPostPostId(Integer postId, NewPostDto body) {
        Post post = postService.editPost(postId, body);

        PostDto postDto = PostMapper.INSTANCE.postToPostDto(post);

        postDto.setUser(userDetailsService.getUserDetailsById(post.getUserId()).get());

        return new ResponseEntity(postDto, HttpStatus.OK);
    }

    private List<PostDto> mapUsersAndLikesToPosts(List<Post> posts) {
        return posts.stream().map(p -> {
            PostDto postDto = PostMapper.INSTANCE.postToPostDto(p);
            postDto.setUser(userDetailsService.getUserDetailsById(p.getUserId()).get());
            postDto.setLikes(likeService.getLikesOfPost(p.getId()));
            postDto.setLiked(likeService.isLikedByUser(p.getId()));
            return postDto;
        }).collect(Collectors.toList());
    }
}
