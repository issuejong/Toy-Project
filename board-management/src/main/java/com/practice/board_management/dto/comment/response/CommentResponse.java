package com.practice.board_management.dto.comment.response;

import com.practice.board_management.domain.comment.Comment;
import com.practice.board_management.dto.comment.CommentDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponse {

    private String userNickname;
    private List<CommentDto> comments;

    public CommentResponse(String userNickname, List<Comment> comments) {
        this.userNickname = userNickname;
        this.comments = comments.stream()
                .map(c -> new CommentDto(c.getContent(), c.getTime()))
                .toList();
    }
}
