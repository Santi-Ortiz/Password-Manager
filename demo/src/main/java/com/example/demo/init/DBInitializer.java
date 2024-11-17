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
    @Override
    public void run(String... args) throws Exception {

        Role role1 = new Role();
        role1.setRolType("USER");
        Role role2 = new Role();
        role2.setRolType("ADMIN");
        roleRepository.save(role1);
        roleRepository.save(role2);

    }
}
