package com.example.secondhand.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class AuthenticationResponse {
    private String token;
    
}
