package com.example.planify.config;

import io.jsonwebtoken.Jwt;
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

    // JWT 토큰 생성
    public String generateToken(String email, Long memberId) {
        return Jwts.builder()
                .setSubject(email) // 이메일
                .claim("memeberId", memberId) // 사용자 ID 추가
                .setIssuedAt(new Date()) // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // HMAC SHA-512 알고리즘을 사용하여 서명
                .compact(); // 최종적으로 JWT 문자열을 생성하여 반환
    }

    // JWT 토큰 검증
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    // 토큰에서 사용자 이메일 추출
    public String getEmailFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}