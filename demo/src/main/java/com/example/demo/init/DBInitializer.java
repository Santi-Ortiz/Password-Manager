package com.example.demo.init;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppRepository appRepository;

    @Override
    public void run(String... args) throws Exception {

        Role role1 = new Role();
        role1.setRolType("USER");
        Role role2 = new Role();
        role2.setRolType("ADMIN");
        roleRepository.save(role1);
        roleRepository.save(role2);

        User user1 = new User();
        user1.setUsername("pepito");
        user1.setPassword("1234");
        user1.setEmail("pepito@example.com");
        user1.setTelephone("123456");
        user1.setRole(role1);
        userRepository.save(user1);

        Account account1 = new Account();
        account1.setPassword("1000.0");
        account1.setUser(user1);
        account1.setApp(appRepository.save(new App()));
        accountRepository.save(account1);

    }
}
