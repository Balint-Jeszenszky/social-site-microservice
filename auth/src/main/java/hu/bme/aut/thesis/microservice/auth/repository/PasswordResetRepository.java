package hu.bme.aut.thesis.microservice.auth.repository;

import hu.bme.aut.thesis.microservice.auth.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {
    Optional<PasswordReset> findByKey(String key);
}
