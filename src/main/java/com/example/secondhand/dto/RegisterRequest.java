package com.example.secondhand.dto;

import com.example.secondhand.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    private String nom;
    private String email;
    private String password;
    private String telephone;
    private String adresse;
     private Role role;

    
}
