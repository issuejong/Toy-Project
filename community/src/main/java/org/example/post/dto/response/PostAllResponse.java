package org.example.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.Post;

@Getter
@AllArgsConstructor
public class PostAllResponse {
    String userNickname;
    String title;
    Post.Tag tag;
}
