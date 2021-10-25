package hu.bme.aut.thesis.microservice.auth.repository;

import hu.bme.aut.thesis.microservice.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("select u from User u where UPPER(u.username) like CONCAT('%', UPPER(:query), '%') or UPPER(u.firstname) like CONCAT('%', UPPER(:query), '%') or UPPER(u.lastname) like CONCAT('%', UPPER(:query), '%')")
    List<User> searchUser(@Param("query") String query);
}
