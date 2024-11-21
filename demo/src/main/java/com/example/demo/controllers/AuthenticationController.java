package com.example.demo.controllers;


import com.example.demo.DTOs.LoginRequestDTO;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/autenticacion")
public class AuthenticationController {

    @Autowired
    private AuthenticationService autenticacionService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        System.out.println("Entra a login endpoint con: " + body.getUsername() + " " + body.getPassword());

        return ResponseEntity.ok(autenticacionService.login(body.getUsername(), body.getPassword())
        );
    }

//    @PostMapping("/recuperar/{nombre}")
//    public ResponseEntity<?> recuperarContraseña(@PathVariable String nombre) {
//        if (!userService.usuarioExistePorNombre(nombre)) {
//            return ResponseEntity.badRequest().body("El usuario no existe");
//        }
//
//        notificacionService.recuperarContrasena(nombre);
//
//        return ResponseEntity.ok(
//                "Se ha enviado un correo con las instrucciones para recuperar la contraseña"
//        );
//    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String token) {
        System.out.println("refresh" + token);
        return ResponseEntity.ok(
                autenticacionService.refresh(token)
        );
    }

}

