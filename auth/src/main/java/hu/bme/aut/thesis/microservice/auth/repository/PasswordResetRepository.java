package hu.bme.aut.thesis.microservice.auth.repository;

import hu.bme.aut.thesis.microservice.auth.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {
    Optional<PasswordReset> findByKey(String key);

    void deleteByUserId(Integer userId);

    @Modifying
    @Query("delete from PasswordReset p where p.expiration < current_timestamp")
    void deleteExpiredKeys();
}
