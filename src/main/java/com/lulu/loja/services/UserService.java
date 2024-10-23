package com.lulu.loja.services;

import com.lulu.loja.dto.LoginRequest;
import com.lulu.loja.models.User;
import com.lulu.loja.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> authenticateUser(String email, String senha){
        Optional<User> opUser = userRepository.findUserByEmail(email);

        if(opUser.isPresent()) {
            User user = opUser.get();

            if (user.isEnabled() && passwordEncoder.matches(senha, user.getPassword())){
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        String token = generateVerificationToken();
        user.setVerificationToken(token);
        return userRepository.save(user);
    }

    public void activateUser(User user){
        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }


    private String generateVerificationToken() {
        // Gera o token de verificação (pode ser UUID, por exemplo)
        return java.util.UUID.randomUUID().toString();
    }
}
