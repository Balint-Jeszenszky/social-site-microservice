package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.userId = :userId or (p.processedMedia = true or p.hasMedia = false) and p.userId in :friends order by p.created desc")
    List<Post> findAllFriendsPosts(@Param("userId") Integer userId, @Param("friends") List<Integer> friends);

    List<Post> getPostsByUserIdOrderByCreatedDesc(Integer userId);

    List<Post> getPostsByUserId(Integer userId);
}
