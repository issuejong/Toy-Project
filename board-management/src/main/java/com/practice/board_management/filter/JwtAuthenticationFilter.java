package com.practice.board_management.filter;

import com.practice.board_management.service.jwt.JwtService;
import com.practice.board_management.service.jwt.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 커스텀 JWT 필터 extends 모든 요청마다 한 번만 동작
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String p = request.getServletPath();
        return p.startsWith("/board/users")       // 회원가입/로그인 등 네가 쓰는 공개 경로
                || p.startsWith("/public")
                || p.startsWith("/actuator/health");  // 필요시 추가
    }

    @Override // 실제 필터 로직. 매 요청 때마다 호출됨 (filterChain에서 자동으로 호출)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // HTTP 헤더에서 Authorization 값을 읽음

        // 토큰이 없거나 Bearer 로 시작하지 않으면 아무 인증도 하지 않고 다음 필터로 패스
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7); // 실제 JWT 문자열만 추출
        String email = jwtService.extractEmail(jwt); // jwt에서 이메일을 꺼냄

        // 이메일이 유효하고 인증 되었는지 확인 후 해당 이메일의 사용자 정보 로드
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 토큰 유효한지 점검 후 인증 객체 생성
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 요청 기반 메타를 Authentication에 세팅
                SecurityContextHolder.getContext().setAuthentication(authToken); // 로그인 상태로 만듦
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘김
    }
}
