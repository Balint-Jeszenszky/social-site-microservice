package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.BadRequestException;
import hu.bme.aut.thesis.microservice.social.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.social.model.Like;
import hu.bme.aut.thesis.microservice.social.repository.LikeRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LoggedInUserService loggedInUserService;

    public void likePost(Integer postId) {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        Like like = new Like(userId, postId);

        try {
            likeRepository.save(like);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Post already liked");
        }
    }

    public void unlikePost(Integer postId) {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        Optional<Like> like = likeRepository.findByUserIdAndPostId(userId, postId);

        if (like.isEmpty()) {
            throw new NotFoundException("Post not liked");
        }

        likeRepository.delete(like.get());
    }

    public Integer getLikesOfPost(Integer postId) {
        return likeRepository.countLikesByPostId(postId);
    }

    public Boolean isLikedByUser(Integer postId) {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        Optional<Like> like = likeRepository.findByUserIdAndPostId(userId, postId);

        return like.isPresent();
    }

    public void deleteAllLikesFromPost(Integer postId) {
        likeRepository.deleteAllByPostId(postId);
    }

    public void deleteAllLikesFromUser(Integer userId) {
        likeRepository.deleteAllByUserId(userId);
    }
}
