package com.example.demo.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Account;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // http://localhost:8080/api/user/find/{id}
    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // http://localhost:8080/api/user/agregar
    @PostMapping("/agregar")
    public ResponseEntity<String> addUser(@RequestBody User user) {

        if(userService.getUserById(user.getUserId()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        try {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }

    // http://localhost:8080/api/user/update/{id}
    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);

        if(existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            user.setUserId(existingUser.getUserId());
            User updatedUser = userService.updateUser(user);
            userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/api/user/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        if(user != null){
            userService.deleteUser(id);
            return new ResponseEntity<>("Usuario eliminado", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Encontrar todas las cuentas que tiene un usuario 
    // http://localhost:8080/api/user/{userId}/accounts
    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getAllAccountsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Account> accounts = userService.getAllAccountsByUser(userId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

     
}
