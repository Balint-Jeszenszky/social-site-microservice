package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.LikedPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedPostRepository extends JpaRepository<LikedPost, Integer> {

}