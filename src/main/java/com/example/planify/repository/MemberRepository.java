package com.example.planify.repository;

import com.example.planify.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);  // ✅ username으로 조회하는 메서드 추가
}
