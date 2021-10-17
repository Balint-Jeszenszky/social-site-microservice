package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> getPostsByUserId(Integer userId);
}
