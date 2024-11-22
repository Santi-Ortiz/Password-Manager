package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Account;
import com.example.demo.entities.App;
import com.example.demo.entities.User;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.AppRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwoFAService twoFAService; // Inyectar TwoFAService

    // Método para validar el token
    private void validateToken() {
        if (!twoFAService.isTokenValid()) {
            throw new IllegalStateException("Invalid token");
        }
    }

    @Transactional
    public Account addAccount(Account account) {
        validateToken(); // Validar el token


        App app = appRepository.findById(account.getApp().getAppId())
                .orElseThrow(() -> new IllegalStateException("App no encontrada"));

        User user = userRepository.findById(account.getUser().getUserId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        System.out.println("App desde servicio: " + app.getName());
        System.out.println("App desde servicio: " + app.getAppId());
        System.out.println("Usuario desde servicio: " + user.getUserId());

        account.setApp(app);
        account.setUser(user);

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Account account) {
        validateToken(); // Validar el token

        Account existingAccount = accountRepository.findById(account.getAccountId())
                .orElseThrow(() -> new IllegalStateException("Cuenta no encontrada"));

        App app = appRepository.findById(account.getApp().getAppId())
                .orElseThrow(() -> new IllegalStateException("App no encontrada"));
        User user = userRepository.findById(account.getUser().getUserId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        System.out.println("Url actualizado: " + app.getUrl());
        System.out.println("contraseña actualizado: " + account.getPassword());
        System.out.println("nombre app actualizado: " + app.getName());
        System.out.println("user id actualizado : " + user.getUserId());
        System.out.println("username actualizado: " + user.getUsername());
        System.out.println("email actualizado: " + user.getEmail());

        app.setUrl(account.getApp().getUrl());
        app.setName(account.getApp().getName());
        app.setDescription(account.getApp().getDescription());
        user.setUsername(account.getUser().getUsername());
        user.setEmail(account.getUser().getEmail());

        appRepository.save(app);
        userRepository.save(user);

        existingAccount.setPassword(account.getPassword());
        existingAccount.setUsernameFromApp(account.getUsernameFromApp());
        existingAccount.setApp(app);
        existingAccount.setUser(user);

        System.out.println("Url actualizado: " + app.getUrl());
        System.out.println("contraseña actualizado: " + account.getPassword());
        System.out.println("nombre app actualizado: " + app.getName());
        System.out.println("user id actualizado : " + user.getUserId());
        System.out.println("username actualizado: " + user.getUsername());
        System.out.println("email actualizado: " + user.getEmail());

        return accountRepository.save(existingAccount);
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        validateToken(); // Validar el token
        System.out.println("Entrando a eliminar cuenta, token ya validado, id de cuenta: " + accountId);

        if (accountRepository.findById(accountId).isPresent()) {
            System.out.println("Cuenta encontrada, eliminando cuenta");
            accountRepository.deleteById(accountId);
        } else {
            System.out.println("Cuenta no encontrada");
            throw new IllegalStateException("Cuenta no encontrada");
        }
    }

    public Account getAccountById(Long accountId) {
        validateToken(); // Validar el token

        if(accountRepository.findById(accountId).isPresent()) {
            Account account = accountRepository.findById(accountId).get();
            return account;
        } else {
            throw new IllegalStateException("Cuenta no encontrada");
        }
    }

    @Transactional
    public List<Account> getAllAccountsByUserId(Long userId){
        return accountRepository.findAllByUser_UserId(userId);
    }

    @Transactional
    public App getAppByAppId(Long appId){
        validateToken(); // Validar el token

        return accountRepository.findByApp_appId(appId);
    }
}
