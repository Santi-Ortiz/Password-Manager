package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Account;
import com.example.demo.entities.User;
import com.example.demo.services.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // http://localhost:8090/api/account/find/{id}
    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id){
        Account account = accountService.getAccountById(id);
        if(account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Account(), HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8090/api/account/agregar
    @PostMapping("/add")
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        
        if(accountService.getAccountById(account.getAccountId()) != null){
            return new ResponseEntity<>("La cuenta ya existe", HttpStatus.CONFLICT);
        }

        try {
            accountService.saveAccount(account);
            return new ResponseEntity<>("Cuenta creada", HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la cuenta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    // http://localhost:8090/api/account/update/{id}
    @PutMapping("update/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long id, @RequestBody Account account) {
        Account existingAccount = accountService.getAccountById(id);

        if(existingAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            account.setAccountId(existingAccount.getAccountId());
            Account updatedAccount = accountService.updateAccount(account);
            accountService.updateAccount(account);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Account(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8090/api/account/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long id){
        Account account = accountService.getAccountById(id);
        if(account != null){
            accountService.deleteAccount(id);
            return new ResponseEntity<>("Cuenta eliminada", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cuenta no encontrada", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8090/api/account/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAllAccountsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Account> accounts = accountService.getAllAccountsByUserId(userId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8090/api/account/app/{appId}
    @GetMapping("/app/{appId}")
    public ResponseEntity<App> getAppByAppId(@PathVariable("appId") Long appId){
        try {
            App app = accountService.getAppByAppId(appId);
            return new ResponseEntity<>(app, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
