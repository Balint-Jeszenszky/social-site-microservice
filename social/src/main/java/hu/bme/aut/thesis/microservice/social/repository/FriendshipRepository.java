package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("select case when f.firstUserId = :userId then f.secondUserId else f.firstUserId end from Friendship f where f.pending = false and f.firstUserId = :userId or f.secondUserId = :userId")
    List<Integer> findUserFriendsById(@Param("userId") Integer userId);
}
