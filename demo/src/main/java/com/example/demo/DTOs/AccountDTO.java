package com.example.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long accountId;
    private String appName; // Nombre de la aplicación asociada
    private String username; // Nombre del usuario propietario de la cuenta
    private String password;
}
