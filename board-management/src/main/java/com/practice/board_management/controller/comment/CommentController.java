package com.practice.board_management.controller.comment;

import com.practice.board_management.dto.comment.request.CommentCreateRequest;
import com.practice.board_management.dto.comment.response.CommentResponse;
import com.practice.board_management.service.comment.CommentService;
import com.practice.board_management.service.jwt.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RestController
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public void createComment(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentCreateRequest request) {
        commentService.createComment(postId, userDetails.getUser(), request);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponse> getComment(@PathVariable Long postId) {
        return commentService.getComment(postId);
    }

    @DeleteMapping("/comments/delete")
    public void deleteComment(Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
    }
}
