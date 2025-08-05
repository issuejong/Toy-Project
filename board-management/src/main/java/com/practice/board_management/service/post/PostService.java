package com.practice.board_management.service.post;

import com.practice.board_management.domain.comment.CommentRepository;
import com.practice.board_management.domain.post.Post;
import com.practice.board_management.domain.post.PostRepository;
import com.practice.board_management.domain.users.User;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.dto.comment.response.CommentResponse;
import com.practice.board_management.dto.post.request.PostCreateRequest;
import com.practice.board_management.dto.post.request.PostModifyRequest;
import com.practice.board_management.dto.post.response.PersonalPostResponse;
import com.practice.board_management.dto.post.response.PostResponse;
import com.practice.board_management.dto.post.response.eachByUser.PostEachByUserResponse;
import com.practice.board_management.dto.post.response.entireUsers.PostEntireUsersResponse;
import com.practice.board_management.dto.user.response.UserNicknameResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void createPost(PostCreateRequest request, User user) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(user)
                .build();
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

    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        post.viewsUp();

        return new PostResponse(post);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        commentRepository.deleteAll(post.getComments());
        postRepository.delete(post);
    }

    @Transactional
    public void modifyPost(User user, Long postId, PostModifyRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.modify(request.getTitle(), request.getContent());
    }
}
