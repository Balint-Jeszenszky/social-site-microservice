package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.ForbiddenException;
import hu.bme.aut.thesis.microservice.social.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.repository.FriendshipRepository;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private LoggedInUserService loggedInUserService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<Post> getPosts() {
        Integer userId = loggedInUserService.getLoggedInUser().getId();
        return postRepository.findAllFriendsPosts(userId, friendshipRepository.findUserFriendsById(userId));
    }

    public List<Post> getPostsByUserId(Integer userId) {
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

        postRepository.delete(post);
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

        if (!loggedInUserService.getLoggedInUser().getId().equals(post.get().getUserId()) || loggedInUserService.isAdmin()) {
            throw new ForbiddenException("No access to post");
        }

        return post.get();
    }
}
