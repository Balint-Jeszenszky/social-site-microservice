package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.repository.FriendshipRepository;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        postRepository.save(post);

        return post;
    }
}
