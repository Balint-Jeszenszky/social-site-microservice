package hu.bme.aut.thesis.microservice.auth.repository;

import hu.bme.aut.thesis.microservice.auth.model.ERole;
import hu.bme.aut.thesis.microservice.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}