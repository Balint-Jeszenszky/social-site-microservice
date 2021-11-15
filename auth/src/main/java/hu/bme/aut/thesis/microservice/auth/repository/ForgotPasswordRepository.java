package hu.bme.aut.thesis.microservice.auth.repository;

import hu.bme.aut.thesis.microservice.auth.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    Optional<ForgotPassword> findByKey(String key);
}
