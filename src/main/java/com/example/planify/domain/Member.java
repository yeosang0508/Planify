package com.example.planify.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")  // 테이블명 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private int authLevel = 1;

    @Column(name = "regDate", nullable = false, updatable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate = LocalDateTime.now();

    @PreUpdate
    public void setUpdateDate(){
        this.updateDate = LocalDateTime.now();
    }
}
