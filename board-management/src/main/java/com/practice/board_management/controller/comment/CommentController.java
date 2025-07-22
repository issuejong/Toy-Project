package com.practice.board_management.controller.comment;

import com.practice.board_management.dto.comment.request.CommentCreateRequest;
import com.practice.board_management.dto.comment.response.CommentResponse;
import com.practice.board_management.service.comment.CommentService;
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
    public void createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest request) {
        commentService.createComment(postId, request);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponse> getComment(@PathVariable Long postId) {
        return commentService.getComment(postId);
    }

}
