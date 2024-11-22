package com.example.demo.services;

import com.example.demo.entities.TwoFA;
import com.example.demo.entities.User;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TwoFAService {

    private TwoFA currentTwoFA; // Instancia única

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") // Inyecta el correo configurado en application.properties
    private String fromEmail;

    public TwoFAService() {
        this.currentTwoFA = new TwoFA();
    }

    // Método para generar un token aleatorio de 7 caracteres (letras y números)
    private String generateRandomToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }

    // Método para crear un nuevo token
    public synchronized void createToken(User userDestination) {
        String tokenValue = generateRandomToken();
        Date expirationTime = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5 minutos desde ahora

        this.currentTwoFA.setValue(tokenValue);
        this.currentTwoFA.setExpirationTime(expirationTime);
        this.currentTwoFA.setInUse(true);
        this.currentTwoFA.setUserDestination(userDestination);

        // Enviar correo con el token
        sendTokenMail(userDestination, tokenValue);

        System.out.println("Token generado: " + tokenValue + " para el usuario: " + userDestination.getUsername());
    }

    // Método para enviar el correo con el token
    private void sendTokenMail(User userDestination, String tokenValue) {
        try {
            String subject = userDestination.getUsername()+"! Tu Token de CryptSafe :0";
            String message = "Hola " + userDestination.getUsername() + ",\n\n" +
                    "Este es tu token de CryptSafe para que puedas usar la app. Ten en cuenta que este token es válido durante 5 minutos.\n\n" +
                    "Token: " + tokenValue + "\n\n" +
                    "Recuerda que esta validación la hacemos por tu seguridad ;)\n\n" +
                    "Gracias por confiar en CryptSafe.";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userDestination.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom(fromEmail);

            mailSender.send(mailMessage);
            System.out.println("Correo enviado exitosamente a " + userDestination.getEmail());
        } catch (MailException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }

    // Método para obtener el token actual
    public TwoFA getCurrentToken() {
        return this.currentTwoFA;
    }

    // Método para verificar si el token es válido
    public boolean isTokenValid() {
        if (currentTwoFA.getExpirationTime() == null) return false;

        Date now = new Date();
        return currentTwoFA.isInUse() && now.before(currentTwoFA.getExpirationTime());
    }

    // Método para marcar el token como usado o liberado
    public void setTokenInUse(boolean inUse) {
        this.currentTwoFA.setInUse(inUse);
    }

    // Tarea programada para verificar la expiración del token
    @Scheduled(fixedRate = 1000) // Ejecuta este método cada segundo
    public synchronized void checkTokenExpiration() {
        if (currentTwoFA.isInUse() && currentTwoFA.getExpirationTime() != null) {
            Date now = new Date();
            if (now.after(currentTwoFA.getExpirationTime())) {
                currentTwoFA.setInUse(false);
                System.out.println("El token ha expirado y ahora está inactivo.");
            }
        }
    }
}
