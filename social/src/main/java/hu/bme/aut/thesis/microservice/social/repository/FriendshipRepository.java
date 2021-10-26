package hu.bme.aut.thesis.microservice.social.repository;

import hu.bme.aut.thesis.microservice.social.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("select case when f.firstUserId = :userId then f.secondUserId else f.firstUserId end from Friendship f where f.pending = false and f.firstUserId = :userId or f.secondUserId = :userId")
    List<Integer> findUserFriendsById(@Param("userId") Integer userId);

    @Query("select f from Friendship f where f.firstUserId = :fromUserId and f.secondUserId = :toUserId and f.pending = true")
    Optional<Friendship> findFriendRequestByUserId(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);

    @Query("select f from Friendship f where f.firstUserId = :fromUserId and f.secondUserId = :toUserId or f.firstUserId = :toUserId and f.secondUserId = :fromUserId")
    Optional<Friendship> findByUserId(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);
}
