package org.example.post.dto.response;

import org.example.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class PostSearchByKeywordResponse {
    List<Post> postList = new ArrayList<>();

    public PostSearchByKeywordResponse(List<Post> postList) {
        this.postList = postList;
    }

    public List<Post> getPostList() {
        return postList;
    }
}
