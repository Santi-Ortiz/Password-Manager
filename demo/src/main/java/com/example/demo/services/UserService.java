package com.example.demo.services;

import com.example.demo.repositories.UserRepository;

import jakarta.transaction.Transactional;

import java.util.Collection;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Account;
import com.example.demo.entities.User;
import com.example.demo.repositories.AccountRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Transactional
    public User saveUser(User user) {
        System.out.println("Entro a servicio saveUser");
        System.out.println("nombre de usuario: " + user.getUsername());

        // Verificar si el nombre de usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("Usuario ya existente con el mismo nombre de usuario");
            throw new IllegalStateException("El nombre de usuario ya está en uso");
        }

        // Verificar si el correo electrónico ya existe
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            System.out.println("Correo electrónico ya en uso");
            throw new IllegalStateException("El correo electrónico ya está en uso");
        }

        System.out.println("Antes de guardar usuario en repo");
        System.out.println("Id de usuario: " + user.getUserId());
        System.out.println("Nombre de usuario: " + user.getUsername());
        System.out.println("Contraseña: " + user.getPassword());
        System.out.println("Rol: " + user.getRole().getRolType() + " con id: " + user.getRole().getId());
        System.out.println("Correo: " + user.getEmail());
        System.out.println("Teléfono: " + user.getTelephone());

        // Guardar el usuario en la base de datos
        userRepository.save(user);
        System.out.println("Usuario guardado exitosamente");
        return user;
    }


    @Transactional
    public User updateUser(User user) {
        if (userRepository.findById(user.getUserId()).isPresent()) {
            User newUser = userRepository.findById(user.getUserId()).get();
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new IllegalStateException("Usuario no encontrado");
        }
    } 

    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalStateException("Usuario no encontrado");
        }
    }

    @Transactional
    public User getUserById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            return user;
        } else {
            throw new IllegalStateException("Usuario no encontrado");
        }
    }

    @Transactional
    public List<Account> getAllAccountsByUser(Long userId){
        if(userRepository.findById(userId).isPresent()){
            User user = userRepository.findById(userId).get();
            List<Account> accounts = accountRepository.findAllByUser_UserId(user.getUserId());
            return accounts;
        } else {
            throw new IllegalStateException("Usuario no encontrado");
        }
    }


    
}
