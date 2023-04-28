package com.warmup.authenticate.repository;

import java.util.Optional;


import com.warmup.authenticate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserId(String userId);
}