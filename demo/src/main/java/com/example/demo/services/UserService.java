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
        if (userRepository.findById(user.getUserId()).isPresent()) {
            throw new IllegalStateException("Usuario ya existente");
        } else {
            User newUser = userRepository.findById(user.getUserId()).get();
            userRepository.save(newUser);
            return newUser;
        }
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
            List<Account> accounts = accountRepository.findAllByUserId(user.getUserId());
            return accounts;
        } else {
            throw new IllegalStateException("Usuario no encontrado");
        }
    }


    
}
