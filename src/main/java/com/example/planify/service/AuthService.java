package com.example.planify.service;


import com.example.planify.config.JwtTokenProvider;
import com.example.planify.dto.AuthRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


/*사용자의 로그인 요청을 처리하고, JWT 토큰을 발급하는 역할을 수행*/
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /*ahtuenticationManager Spring Security의 인증 관리 매니저, jwtTokenProvider JWT 토큰을 생성하는 클래스*/
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*사용자의 인증을 처리하고 JWT 토큰을 반환
    * authRequest 사용자 인증 요청 DTO
    * 인증 성공 시 JWT 발급*/
    public String authenticate(AuthRequestDTO authRequest) {
        // DTO에서 이메일과 사용자 번호
        String email = authRequest.getEmail();
        Long memberId = authRequest.getId();
        String password = authRequest.getPassword();

        // Spring Security의 AuthenticationManager를 사용하여 사용자 인증 수행
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // 인증이 성공하면 JWT 토큰을 생성하여 반환
        return jwtTokenProvider.generateToken(email, memberId);
    }
}