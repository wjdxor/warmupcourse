package com.warmup.config.security;

import com.warmup.controller.BoardController;
import com.warmup.service.UserService;
import com.warmup.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰이 없거나 Bearer 로 시작하지 않으면 필터를 타지 않음
        if (authorization == null || !authorization.startsWith("Bearer")) {
            logger.info("authentication is null");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰이 있으면 토큰을 검증하고 유저 정보를 받아옴
        String token = authorization.split(" ")[1];

        // 토큰이 expired 되었는지 확인
        if (JwtUtil.isExpired(token, secretKey)) {
            logger.info("token is expired");
            filterChain.doFilter(request, response);
            return;
        }


        String userId = JwtUtil.getUserId(token, secretKey);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
