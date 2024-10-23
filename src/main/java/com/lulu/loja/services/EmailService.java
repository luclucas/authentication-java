package com.lulu.loja.services;

import com.lulu.loja.models.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void emailVerificationTest(){
        String subject = "Testando";
        String message = "Olá, meu chapa";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("luclucas.08@gmail.com");
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    public void sendVerificationEmail(User user, String token) {
        String subject = "Verificação de Email";
        String confirmationUrl = "http://localhost:8080/auth/verify?token=" + token;
        String message = "Por favor, clique no link abaixo para verificar seu email:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
