package com.study.springsecuritystudy.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override //인가의 시작*****
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(req); //Header에서 req로 받아온다.
        if (StringUtils.hasText(tokenValue)) { //내용물이 있는지 확인
            tokenValue = jwtUtil.substringToken(tokenValue); //토큰만 떼어옴
            log.info(tokenValue);

            if (!jwtUtil.validateToken(tokenValue)) { //토큰 검증 - Signature로 확인
                log.error("Token Error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue); //토큰에서 사용자 정보를 가져옴
            try {
                setAuthentication(info.getSubject()); //
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }


    public void setAuthentication(String username) { //다른 security와의 융합을 위해 만든 code의 전부?
        SecurityContext context = SecurityContextHolder.createEmptyContext(); //빈 security Context 생성
        Authentication authentication = createAuthentication(username); //username을 기준으로 Authentication에 대입
        context.setAuthentication(authentication); //context 에 넣어줌

        SecurityContextHolder.setContext(context); //holder에 넣어줌
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username); //Userdetail 사용, DB에서 확인
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
