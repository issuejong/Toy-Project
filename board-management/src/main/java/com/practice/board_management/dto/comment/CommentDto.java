package com.practice.board_management.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {

    private String content;
    private LocalDateTime createdAt;

    public CommentDto(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }
}
