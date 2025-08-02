package com.practice.board_management.controller.user;

import com.practice.board_management.dto.user.request.UserCreateRequest;
import com.practice.board_management.dto.user.request.UserLoginRequest;
import com.practice.board_management.dto.user.response.UserLoginResultResponse;
import com.practice.board_management.dto.user.response.UserResponse;
import com.practice.board_management.service.jwt.JwtService;
import com.practice.board_management.service.jwt.UserDetailsImpl;
import com.practice.board_management.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board/users")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody UserCreateRequest request) {
        userService.signUp(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String accessToken) {
        String email = jwtService.extractEmail(accessToken.replace("Bearer ", ""))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰"));

        jwtService.destroyRefreshToken(email);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request) {
        String refreshToken = jwtService.extractRefreshToken(request)
                .orElseThrow(IllegalArgumentException::new);
        System.out.println("리프레시"+refreshToken);

        if (!jwtService.isTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 Refresh Token입니다.");
        }
        System.out.println("유효성"+refreshToken);

        String email = jwtService.extractEmail(refreshToken)
                .orElseThrow(IllegalArgumentException::new);
        System.out.println("이메일"+email);
        String newAccessToken = jwtService.createAccessToken(email);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .body("Access Token 재발급 완료");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails.getUsername());
    }

}
