package org.example.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailReponse {
    String userNickname;
    String title;
    String content;
}
