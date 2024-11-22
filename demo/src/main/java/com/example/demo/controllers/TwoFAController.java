package com.example.demo.controllers;

import com.example.demo.entities.TwoFA;
import com.example.demo.entities.User;
import com.example.demo.services.TwoFAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/twofa")
public class TwoFAController {

    @Autowired
    private TwoFAService twoFAService;

    // 1. Endpoint para crear un nuevo token
    @PostMapping("/create")
    public ResponseEntity<String> createToken(@RequestBody User user) {
        try {
            twoFAService.createToken(user);
            return new ResponseEntity<>("Token generado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al generar el token: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. Endpoint para comparar un string con el token actual
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
        try {
            TwoFA currentToken = twoFAService.getCurrentToken();
            boolean isValid = token.equals(currentToken.getValue());
            return new ResponseEntity<>(isValid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. Endpoint para verificar si el token está en uso
    @GetMapping("/is-in-use")
    public ResponseEntity<Boolean> isTokenInUse() {
        try {
            boolean inUse = twoFAService.getCurrentToken().isInUse();
            return new ResponseEntity<>(inUse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
