package com.example.planify.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
    private Long id;
}