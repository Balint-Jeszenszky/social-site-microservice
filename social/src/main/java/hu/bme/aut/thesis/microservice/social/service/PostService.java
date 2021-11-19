package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.ForbiddenException;
import hu.bme.aut.thesis.microservice.social.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.MediaStatusDto;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private LoggedInUserService loggedInUserService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private MediaAccessService mediaAccessService;

    @Autowired
    private LikeService likeService;

    public List<Post> getPosts() {
        Integer userId = loggedInUserService.getLoggedInUser().getId();
        return postRepository.findAllFriendsPosts(userId, friendshipService.getFriendIdsForUser(userId));
    }

    public List<Post> getPostsByUserId(Integer userId) {
        Integer loggedInUserId = loggedInUserService.getLoggedInUser().getId();

        if (!loggedInUserId.equals(userId) && !friendshipService.areFriends(loggedInUserId, userId) && !loggedInUserService.isAdmin()) {
            return Collections.emptyList();
        }

        return postRepository.getPostsByUserIdOrderByCreatedDesc(userId);
    }

    public Post createPost(NewPostDto newPost) {
        Post post = new Post(
                loggedInUserService.getLoggedInUser().getId(),
                newPost.getText()
        );

        post.setHasMedia(newPost.isMedia());
        if (newPost.isMedia()) {
            post.setProcessedMedia(false);
        }

        postRepository.save(post);

        return post;
    }

    public void deletePost(Integer postId) {
        Post post = getPost(postId);

        if (post.getHasMedia()) {
            mediaAccessService.deleteMedia(postId);
        }

        postRepository.delete(post);

        likeService.deleteAllLikesFromPost(post.getId());
    }

    public Post editPost(Integer postId, NewPostDto body) {
        Post post = getPost(postId);

        post.setText(body.getText());

        postRepository.save(post);

        return post;
    }

    private Post getPost(Integer postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new NotFoundException("Post not found");
        }

        if (!loggedInUserService.getLoggedInUser().getId().equals(post.get().getUserId()) && !loggedInUserService.isAdmin()) {
            throw new ForbiddenException("No access to post");
        }

        return post.get();
    }

    public void setPostMediaStatus(Integer id, MediaStatusDto.StatusEnum status, String name) {

        if (status == MediaStatusDto.StatusEnum.FAILED) {
            postRepository.deleteById(id);
            return;
        }

        Post post = postRepository.getById(id);

        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        if (status == MediaStatusDto.StatusEnum.PROCESSING) {
            post.setMediaName(name);
        } else if (status == MediaStatusDto.StatusEnum.AVAILABLE) {
            post.setProcessedMedia(true);
        }
        postRepository.save(post);
    }
}
