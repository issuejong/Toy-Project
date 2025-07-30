package com.practice.board_management.service.user;

import com.practice.board_management.domain.users.User;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.dto.user.request.UserCreateRequest;
import com.practice.board_management.dto.user.request.UserLoginRequest;
import com.practice.board_management.dto.user.response.UserLoginResultResponse;
import com.practice.board_management.dto.user.response.UserResponse;
import com.practice.board_management.service.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserCreateRequest request) {
        User user = new User(request.getPassword(), request.getNickname(), request.getEmail(), request.getRole());
        userRepository.save(user);
    }

    @Transactional
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void signUp(UserCreateRequest request) {
        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(password, request.getNickname(), request.getEmail(), request.getRole());
        userRepository.save(user);
    }

    @Transactional
    public UserLoginResultResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        System.out.println("서비스진입");
        String accessToken = jwtService.createAccessToken(user.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        System.out.println(refreshToken + "리프레시토큰발급완료");

        jwtService.updateRefreshToken(user.getEmail(), refreshToken);
        System.out.println(user.getRefreshToken()+"jwt서비스 나옴");

        return new UserLoginResultResponse(user.getUserId(), accessToken, refreshToken);
    }
}
