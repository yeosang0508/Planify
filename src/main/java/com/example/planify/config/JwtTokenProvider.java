package com.example.planify.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // application.properties에서 설정된 JWT 비밀키 값
    @Value("${jwt.secret}")
    private String jwtSecret;

    // JWT의 만료 시간
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /*주어진 사용자명을 기반으로 JWT 토큰을 생성하는 메서드 */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 토큰의 주체(사용자명) 설정
                .setIssuedAt(new Date()) // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // HMAC SHA-512 알고리즘을 사용하여 서명
                .compact(); // 최종적으로 JWT 문자열을 생성하여 반환
    }
}