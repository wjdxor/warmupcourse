package com.warmup.config;

import com.warmup.authenticate.entity.Member;
import com.warmup.authenticate.repository.MemberRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> optional = memberRepository.findByUserId(userId);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(userId + " 사용자 없음");
        } else {
            Member member = optional.get();
            return new SecurityUser(member);
        }

    }

}