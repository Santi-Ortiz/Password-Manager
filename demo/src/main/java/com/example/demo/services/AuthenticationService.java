package com.example.demo.services;


import java.net.PasswordAuthentication;
import java.util.Optional;

import com.example.demo.DTOs.LoginResponseDTO;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(String username, String password) {
        System.out.println("Entra a login service con: " + username + " " + password);

        // Buscar el usuario por username
        Optional<User> usuario = userRepository.findByUsername(username);

        if (usuario.isEmpty()) {
            System.out.println("Usuario no encontrado");
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Verificar la contraseña usando passwordEncoder
        User user = usuario.get();
        System.out.println("Usuario encontrado:" + user.getUsername() + " contraseña: " + user.getPassword());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Contraseña incorrecta");
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        // Autenticar al usuario con el AuthenticationManager (opcional)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Generar token JWT
        String token = jwtService.generateToken(user);

        // Retornar la respuesta
        return new LoginResponseDTO(token, username, user.getRole().getRolType(), user.getUserId(), user.isEnabled());
    }

    public LoginResponseDTO refresh(String token) {
        token = token.substring(7);
        String username = jwtService.extractUserName(token);
        Optional<User> usuario = userRepository.findByUsername(username);

        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        token = jwtService.generateToken(usuario.get());

        return new LoginResponseDTO(token, username, usuario.get().getRole().getRolType(), usuario.get().getUserId(), usuario.get().isEnabled());
    }

}
