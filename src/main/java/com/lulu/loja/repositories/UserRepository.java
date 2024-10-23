package com.lulu.loja.repositories;

import com.lulu.loja.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    // Método para buscar um usuário pelo token de verificação
    Optional<User> findByVerificationToken(String token);

}
