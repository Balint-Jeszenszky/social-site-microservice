package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(Integer postId);
    Integer countCommentsByPostId(Integer postId);
    void deleteAllByUserId(Integer userId);
    void deleteAllByPostId(Integer postId);
}
