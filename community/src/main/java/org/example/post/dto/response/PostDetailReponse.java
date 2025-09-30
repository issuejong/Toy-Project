package org.example.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.comment.dto.response.CommentResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostDetailReponse {
    String userNickname;
    String title;
    String content;
    List<CommentResponse> commentResponse;
}
