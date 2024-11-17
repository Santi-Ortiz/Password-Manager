package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account saveAccount(Account account) {
        if (accountRepository.findById(account.getAccountId()).isPresent()) {
            throw new IllegalStateException("Cuenta ya existente");
        } else {
            Account newAccount = accountRepository.findById(account.getAccountId()).get();
            accountRepository.save(newAccount);
            return newAccount;
        }
    }

    @Transactional
    public Account updateAccount(Account account) {
        if (accountRepository.findById(account.getAccountId()).isPresent()) {
            Account newAccount = accountRepository.findById(account.getAccountId()).get();
            accountRepository.save(newAccount);
            return newAccount;
        } else {
            throw new IllegalStateException("Cuenta no encontrada");
        }
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
}
