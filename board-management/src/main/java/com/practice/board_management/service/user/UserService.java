package com.practice.board_management.service.user;

import com.practice.board_management.domain.users.User;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.dto.user.request.UserCreateRequest;
import com.practice.board_management.dto.user.response.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
