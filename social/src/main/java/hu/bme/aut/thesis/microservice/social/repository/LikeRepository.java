package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserIdAndPostId(Integer userId, Integer postId);
    Integer countLikesByPostId(Integer postId);
}
