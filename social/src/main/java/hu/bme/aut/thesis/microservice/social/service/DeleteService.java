package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.ForbiddenException;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.repository.CommentRepository;
import hu.bme.aut.thesis.microservice.social.repository.FriendshipRepository;
import hu.bme.aut.thesis.microservice.social.repository.LikeRepository;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DeleteService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private LoggedInUserService loggedInUserService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MediaAccessService mediaAccessService;

    @Transactional
    public void deleteUser(Integer userId) {
        Integer loggedInUserId = loggedInUserService.getLoggedInUser().getId();

        if (loggedInUserId.equals(userId) && loggedInUserService.isAdmin()) {
            throw new ForbiddenException("Can not delete admin user");
        }

        if (!loggedInUserId.equals(userId) && !loggedInUserService.isAdmin()) {
            throw new ForbiddenException("Wrong user");
        }

        friendshipRepository.deleteAllFriendsByUserId(userId);

        likeRepository.deleteAllByUserId(userId);

        commentRepository.deleteAllByUserId(userId);

        List<Post> posts = postRepository.getPostsByUserId(userId);

        for (Post post : posts) {
            likeRepository.deleteAllByPostId(post.getId());
            commentRepository.deleteAllByPostId(post.getId());
            mediaAccessService.deleteMedia(post.getId());
        }

        postRepository.deleteAll(posts);
    }
}
