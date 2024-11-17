package com.example.demo.services;


import java.util.Optional;

import com.example.demo.DTOs.LoginResponseDTO;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO login(String username, String password) {
        Optional<User> usuario = userRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtService.generateToken(usuario.get());
        return new LoginResponseDTO(token, username, usuario.get().getRole().getRolType());
    }

    public LoginResponseDTO refresh(String token) {
        token = token.substring(7);
        String username = jwtService.extractUserName(token);
        Optional<User> usuario = userRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        token = jwtService.generateToken(usuario.get());

        return new LoginResponseDTO(token, username, usuario.get().getRole().getRolType());
    }

}
