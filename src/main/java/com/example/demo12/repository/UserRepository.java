package com.example.demo12.repository;

import com.example.demo12.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Vérifie si un utilisateur existe déjà par son email

}
