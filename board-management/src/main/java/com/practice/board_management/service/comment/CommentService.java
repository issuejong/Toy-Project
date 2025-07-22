package com.practice.board_management.service.comment;

import com.practice.board_management.domain.comment.Comment;
import com.practice.board_management.domain.comment.CommentRepository;
import com.practice.board_management.domain.post.Post;
import com.practice.board_management.domain.post.PostRepository;
import com.practice.board_management.domain.users.User;
import com.practice.board_management.domain.users.UserRepository;
import com.practice.board_management.dto.comment.request.CommentCreateRequest;
import com.practice.board_management.dto.comment.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {

    UserRepository userRepository;
    PostRepository postRepository;
    CommentRepository commentRepository;

    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void createComment(Long postId, CommentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = new Comment(post, user, request.getContent());

        commentRepository.save(comment);
    }

    public List<CommentResponse> getComment(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findCommentsByPostId(postId);

        Map<User, List<Comment>> grouped = comments.stream()
                .collect(Collectors.groupingBy(Comment::getAuthor));

        return  grouped.entrySet().stream()
                .map(e -> new CommentResponse(e.getKey().getNickname(), e.getValue()))
                .toList();
    }

}
