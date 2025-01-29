package com.example.planify.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")  // 테이블명 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    private Long id;

    @Column(nullable = false, unique = true)  // 중복 방지
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;  // ROLE_USER, ROLE_ADMIN 등 저장 가능

}
