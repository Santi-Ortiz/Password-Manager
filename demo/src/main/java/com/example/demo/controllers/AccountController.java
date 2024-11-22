package com.example.demo.controllers;

import com.example.demo.entities.Account;
import com.example.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Crear una nueva cuenta
    @PostMapping("/add")
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        try {
            accountService.addAccount(account);
            return new ResponseEntity<>("Cuenta creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la cuenta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener una cuenta por ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar una cuenta
    @PutMapping("/update/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        try {
            account.setAccountId(id);
            Account updatedAccount = accountService.updateAccount(account);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK); 
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // Eliminar una cuenta
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId) {
        try {
            accountService.deleteAccount(accountId);
            return new ResponseEntity<>("Cuenta eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la cuenta: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las cuentas de un usuario
    @GetMapping("/user-accounts/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Account> accounts = accountService.getAllAccountsByUserId(userId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
