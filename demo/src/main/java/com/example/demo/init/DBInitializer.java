package com.example.demo.init;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Dotenv dotenv = Dotenv.load();
        // System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
        // System.setProperty("DATABASE_USER", dotenv.get("DATABASE_USER"));
        // System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
        // System.setProperty("JWT_SIGNING_KEY", dotenv.get("JWT_SIGNING_KEY"));

        Role role1 = new Role();
        role1.setRolType("USER");
        Role role2 = new Role();
        role2.setRolType("ADMIN");
        roleRepository.save(role1);
        roleRepository.save(role2);

        User user1 = new User();
        user1.setUsername("pepito");
        user1.setPassword(passwordEncoder.encode("hola1234"));
        user1.setEmail("juando02603@gmail.com");
        user1.setTelephone("123456");
        user1.setRole(role1);
        user1.setEnabled(true);
        userRepository.save(user1);

        App app1 = new App();
        app1.setName("Youtube App 1");
        app1.setUrl("https://www.youtube.com");
        app1.setDescription("Youtube App 1 Description");
        appRepository.save(app1);

        Account account1 = new Account();
        account1.setUsernameFromApp("pepitoFromAppYout");
        account1.setPassword("myAppPassword>Yout");
        account1.setUser(user1);
        account1.setApp(appRepository.save(app1));
        accountRepository.save(account1);

        App app2 = new App();
        app2.setName("Facebook App 2");
        app2.setUrl("https://www.facebook.com");
        app2.setDescription("Facebook App 2 Description");
        appRepository.save(app2);

        Account account2 = new Account();
        account2.setUsernameFromApp("pepitoFromAppFace");
        account2.setPassword("myAppPasswordFace");
        account2.setUser(user1);
        account2.setApp(appRepository.save(app2));
        accountRepository.save(account2);

        //SpringApplication.run(DBInitializer.class, args);

    }
}
