package com.example.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String username;
    private String role;
    private Long userId;
    private boolean enabled;
}
