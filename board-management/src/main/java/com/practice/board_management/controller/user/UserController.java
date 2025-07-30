package com.practice.board_management.controller.user;

import com.practice.board_management.dto.user.request.UserCreateRequest;
import com.practice.board_management.dto.user.request.UserLoginRequest;
import com.practice.board_management.dto.user.response.UserLoginResultResponse;
import com.practice.board_management.dto.user.response.UserResponse;
import com.practice.board_management.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @PostMapping("/login")
    public UserLoginResultResponse login(@RequestBody UserLoginRequest request) {
        System.out.println("gd");
        return userService.login(request);
    }


}
