package com.practice.board_management.service.post;

import com.practice.board_management.domain.post.Post;
import com.practice.board_management.domain.post.PostRepository;
import com.practice.board_management.domain.users.User;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.dto.post.request.PostCreateRequest;
import com.practice.board_management.dto.post.response.PersonalPostResponse;
import com.practice.board_management.dto.post.response.eachByUser.PostEachByUserResponse;
import com.practice.board_management.dto.post.response.entireUsers.PostEntireUsersResponse;
import com.practice.board_management.dto.user.response.UserNicknameResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createPost(PostCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        Post post = new Post(user, request.getTitle(), request.getContent());
        postRepository.save(post);
    }

    @Transactional
    public PostEachByUserResponse getPostByPersonal(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        List<PersonalPostResponse> posts = postRepository.findAllByAuthor(user).stream()
                .map(PersonalPostResponse::new)
                .toList();

        return new PostEachByUserResponse(user.getNickname(), posts);
    }

    @Transactional
    public PostEntireUsersResponse getPostByEntire(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        if (user.getRole() != User.Role.ADMIN)
            throw new IllegalArgumentException("관리자가 아닙니다.");

        List<Post> allPosts = postRepository.findAllWithAuthor(); // 또는 findAll() + @EntityGraph

        Map<User, List<PersonalPostResponse>> grouped = allPosts.stream()
                .collect(Collectors.groupingBy(
                        Post::getAuthor,
                        Collectors.mapping(PersonalPostResponse::new, Collectors.toList())
                ));

        List<PostEachByUserResponse> result = grouped.entrySet().stream()
                .map(e -> new PostEachByUserResponse(e.getKey().getNickname(), e.getValue()))
                .toList();

        return new PostEntireUsersResponse(result);
    }

    /*
    @Transactional
    public PostEntireUsersResponse getPostByEntire(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        if(user.getRole() != User.Role.ADMIN) throw new IllegalArgumentException("관리자가 아닙니다.");

        List<PostEachByUserResponse> responses = new ArrayList<>();
        List<User> users = userRepository.findAll().stream().toList();

        for(User tempUser : users) {
            List<PersonalPostResponse> response = postRepository.findAllByAuthor(tempUser).stream()
                    .map(PersonalPostResponse::new)
                    .toList();
            responses.add(new PostEachByUserResponse(tempUser.getNickname(), response));
        }
        */
}
