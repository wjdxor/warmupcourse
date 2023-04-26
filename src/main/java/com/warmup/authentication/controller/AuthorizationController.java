package com.warmup.authentication.controller;

import com.warmup.authentication.dto.MemberJoinDto;
import com.warmup.authentication.dto.MemberLoginDto;
import com.warmup.authentication.service.MemberService;
import com.warmup.authentication.service.RegisterMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final MemberService memberService;
    private final RegisterMemberService registerMemberService;

    public AuthorizationController(MemberService memberService, RegisterMemberService registerMemberService) {
        this.memberService = memberService;
        this.registerMemberService = registerMemberService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinDto dto) {
        try {
            registerMemberService.join(dto.getUserid(), dto.getPw());
            return ResponseEntity.ok("join success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public boolean login(MemberLoginDto dto) {
        System.out.println("dto: " + dto.getPw());
        return memberService.isValidMember(dto.getUserid(), dto.getPw());
    }
}
