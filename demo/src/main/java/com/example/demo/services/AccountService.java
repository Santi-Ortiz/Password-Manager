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

    @Transactional
    public Account addAccount(Account account) {
        App app = appRepository.findById(account.getApp().getAppId())
            .orElseThrow(() -> new IllegalStateException("App no encontrada"));

        User user = userRepository.findById(account.getUser().getUserId())
            .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        account.setApp(app);
        account.setUser(user);

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Account account) {
        Account existingAccount = accountRepository.findById(account.getAccountId())
                .orElseThrow(() -> new IllegalStateException("Cuenta no encontrada"));

        App app = appRepository.findById(account.getApp().getAppId())
                .orElseThrow(() -> new IllegalStateException("App no encontrada"));
        User user = userRepository.findById(account.getUser().getUserId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        existingAccount.setPassword(account.getPassword());
        existingAccount.setApp(app);
        existingAccount.setUser(user); 

        return accountRepository.save(existingAccount);
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            accountRepository.deleteById(accountId);
        } else {
            throw new IllegalStateException("Cuenta no encontrada");
        }
    }

    public Account getAccountById(Long accountId) {
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
        return accountRepository.findByApp_appId(appId);
    }
}
