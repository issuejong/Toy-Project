package org.example.comment.service;

import org.example.comment.dto.request.CommentCreateRequest;
import org.example.comment.dto.response.CommentResponse;
import org.example.comment.repository.CommentRepository;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.BusinessException;
import org.example.exception.ErrorCode;
import org.example.post.repository.PostRepository;
import org.example.user.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
        Comment comment = new Comment(request.content(), post, user);
        commentRepository.save(comment);
    }
}
