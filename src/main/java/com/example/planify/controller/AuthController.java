package com.example.planify.controller;

import com.example.planify.config.JwtTokenProvider;
import com.example.planify.domain.Member;
import com.example.planify.dto.AuthRequestDTO;
import com.example.planify.dto.AuthResponseDTO;
import com.example.planify.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*로그인 요청을 받아 인증을 수행하고, JWT 토큰을 쿠키에 저장하여 반환*/
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest, HttpServletResponse response){

        // 1. 이메일로 사용자 찾기
        Member member = memberRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않은 회원입니다."));

        if (!passwordEncoder.matches(authRequest.getPassword(), member.getPassword())) {
            throw new RuntimeException("일치하지 않는 비밀번호입니다.");
        }

        // 3. JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(member.getEmail(), member.getId());

        // 4. JWT 토큰을 HTTP-only 쿠키에 저장
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가하도록 설정
        cookie.setPath("/"); // 애플리케이션 전역에서 사용 가능하도록 설정
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효

        // 5. 응답에 쿠키 추가
        response.addCookie(cookie);

        // 6. 성공 응답 반환
        return ResponseEntity.ok(new AuthResponseDTO("로그인 되었습니다."));
    }
}
