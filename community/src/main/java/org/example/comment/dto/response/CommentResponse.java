package org.example.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponse {
    String userNickname;
    String content;
}
