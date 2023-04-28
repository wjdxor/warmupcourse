package com.warmup.authenticate.dto;

import com.warmup.authenticate.entity.Member;
import com.warmup.authenticate.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberSignUpReqDto {
    private String userId;
    private String password;
    private Role role;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .userId(userId)
                .password(password)
                .role(Role.ROLE_USER)
                .build();
    }
}
