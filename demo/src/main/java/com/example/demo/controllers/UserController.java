package com.example.demo.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Account;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // http://localhost:8090/api/user/find/{id}
    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // http://localhost:8090/api/user/agregar
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User user) {
        System.out.println("Entro a endpoint saveUser");

        try {
            System.out.println("Antes de guardar usuario");
            userService.saveUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario creado");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            // Manejar errores de validación específicos
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
            // Manejar errores genéricos
            System.out.println("Error al guardar usuario");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al crear el usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // http://localhost:8090/api/user/update/{id}
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

    // http://localhost:8090/api/user/delete/{id}
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
    // http://localhost:8090/api/user/{userId}/accounts
    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getAllAccountsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Account> accounts = userService.getAllAccountsByUser(userId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/activate/{id}")
    public ResponseEntity<?> activarUsuario(@PathVariable Long id) {
        try {
            User usuario = userService.activateUser(id);
            return ResponseEntity.ok("Usuario activado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
