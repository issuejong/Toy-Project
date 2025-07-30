package com.practice.board_management.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.board_management.domain.users.User;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessJWTProvideHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        log.info("로그인 성공: {}", email);

        // 1. JWT 생성
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        // 2. DB에 RefreshToken 저장
        userRepository.findByEmail(email).ifPresent(user -> {
            user.updateRefreshToken(refreshToken);
            userRepository.save(user); // 보장용, 트랜잭션 안이면 꼭 save 필요
        });

        // 3. 응답 반환
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(),
                new TokenResponse(accessToken, refreshToken));
    }

    // 응답용 DTO
    record TokenResponse(String accessToken, String refreshToken) {}
}