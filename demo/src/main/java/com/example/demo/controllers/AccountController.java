package com.example.demo.controllers;

import com.example.demo.entities.Account;
import com.example.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Crear una nueva cuenta
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody Account account) {
        try {
            System.out.println("Entrando a endpoint de agregar cuenta");
            System.out.println("Cuenta: " + account.getAccountId());
            System.out.println("Id de app: " + account.getApp().getAppId());
            System.out.println("Contraseña: " + account.getPassword());
            System.out.println("Id de user: " + account.getUser().getUserId());

            accountService.addAccount(account);
            System.out.println("Cuenta creada exitosamente");

            // Respuesta JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cuenta creada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error al crear la cuenta: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la cuenta: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    // Obtener una cuenta por ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar una cuenta
    @PutMapping("/update/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        try {
            System.out.println("Entrando a endpoint de actualizar cuenta");
            account.setAccountId(id);
            Account updatedAccount = accountService.updateAccount(account);
            System.out.println("Saliendo a endpoint de actualizar cuenta");
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // Eliminar una cuenta
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId) {
        System.out.println("Entrando a endpoint de eliminar cuenta");
        try {
            accountService.deleteAccount(accountId);
            System.out.println("Cuenta eliminada exitosamente controller");
            return ResponseEntity.status(HttpStatus.OK).build();    
        } catch (Exception e) {
            System.out.println("Error al eliminar la cuenta: " + e.getMessage());
            return new ResponseEntity<>("Error al eliminar la cuenta: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las cuentas de un usuario
    @GetMapping("/user-accounts/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Account> accounts = accountService.getAllAccountsByUserId(userId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
