package org.example.comment.controller;

import org.example.comment.dto.request.CommentCreateRequest;
import org.example.comment.dto.response.CommentResponse;
import org.example.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> createComment(@PathVariable("postId") Long postId,
                                                              @RequestBody CommentCreateRequest request) {
        commentService.createComment(postId, request);
        return ResponseEntity.ok().body("댓글이 작성되었습니다.");
    }
}
