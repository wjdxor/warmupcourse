package com.warmup.authentication.service;

import com.warmup.authentication.domain.Member;
import com.warmup.authentication.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Optional<Member> findOne(String userId) {
        return repository.findByUserid(userId);
    }

    public boolean isValidMember(String userId, String password) {
        Optional<Member> member = findOne(userId);
        if (member.isPresent()) {
            return member.get().getPw().equals(password);
        }
        return false;
    }
}
