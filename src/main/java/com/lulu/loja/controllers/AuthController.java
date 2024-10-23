package com.lulu.loja.controllers;

import com.lulu.loja.dto.LoginRequest;
import com.lulu.loja.models.User;
import com.lulu.loja.services.EmailService;
import com.lulu.loja.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final EmailService emailService;
    private final UserService userService;

    public AuthController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        Optional<User> opUser = userService.authenticateUser(request.getEmail(), request.getPassword());

        if(opUser.isPresent()){
            User user = opUser.get();

            user.setPassword(null);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao logar");
    }

    @PostMapping("/register")
    public String registUser(@RequestBody User user){
        User registeredUser = userService.registerUser(user);
        emailService.sendVerificationEmail(registeredUser, registeredUser.getVerificationToken());  // Envia o email de verificação
        return "Usuário registrado com sucesso! Verifique seu email para ativar a conta.";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token){
        Optional<User> optionalUser = userService.findByVerificationToken(token);
        if (optionalUser.isEmpty()){
            return "token inválido";
        }
        userService.activateUser(optionalUser.get());
        return "conta ativada com sucesso";
    }
}
