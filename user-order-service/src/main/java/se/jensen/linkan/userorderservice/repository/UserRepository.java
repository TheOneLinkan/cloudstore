package se.jensen.linkan.userorderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.linkan.userorderservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}