package com.practice.board_management.dto.comment.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateRequest {

    private Long userId;
    private String content;

}
