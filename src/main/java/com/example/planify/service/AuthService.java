package com.example.planify.service;


import com.example.planify.config.JwtTokenProvider;
import com.example.planify.dto.AuthRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authenticate(AuthRequestDTO authRequest) {
        // DTO에서 올바르게 값을 가져옴
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        // Spring Security에서 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // JWT 발급
        return jwtTokenProvider.generateToken(username);
    }
}