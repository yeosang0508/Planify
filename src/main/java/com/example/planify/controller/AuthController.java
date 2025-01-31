package com.example.planify.controller;

import com.example.planify.dto.AuthRequestDTO;
import com.example.planify.dto.AuthResponseDTO;
import com.example.planify.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*로그인 요청을 받아 인증을 수행하고, JWT 토큰을 쿠키에 저장하여 반환*/
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /*사용자 인증 후 JWT 토큰을 발급하고, 이를 HTTP-only 쿠키에 저장하여 클라이언트에 반환
    * authRequest 로그인 요청 DTO
    * response HTTP 응답 객체 */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest, HttpServletResponse response) {
        /*사용자의 인증을 수행하고 JWT 토큰을 발급*/
        String token = authService.authenticate(authRequest);

        /*JWT 토큰을 HTTP-only 쿠키에 저장하여 클라이언트에 반환*/
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가하도록 설정
        cookie.setPath("/"); // 애플리케이션 전역에서 사용 가능하도록 설정
        cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효 기간 : 7일

        // 응답에 쿠키 추가
        response.addCookie(cookie);

        // 로그인 성공 응답 반환
        return ResponseEntity.ok(new AuthResponseDTO("Login successful"));
    }
}