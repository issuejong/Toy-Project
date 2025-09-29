package org.example.user.service;

import org.example.entity.User;
import org.example.user.repository.UserRepository;
import org.example.user.dto.UserCreateRequest;
import org.example.exception.BusinessException;
import org.example.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_USER_EMAIL);
        }
        userRepository.save(new User(request.email(), request.nickname()));
    }
}
