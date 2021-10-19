package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.userId = :userId or p.userId in :friends order by p.created desc")
    List<Post> findAllFriendsPosts(Integer userId, List<Integer> friends);

    List<Post> getPostsByUserIdOrderByCreatedDesc(Integer userId);
}
