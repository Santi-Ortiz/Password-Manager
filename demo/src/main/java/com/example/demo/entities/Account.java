package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @OneToOne
    @JoinColumn(name = "app_id", nullable = false)
    private App app; // Relación uno a uno con App

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Relación muchos a uno con User

    private String password;
}
