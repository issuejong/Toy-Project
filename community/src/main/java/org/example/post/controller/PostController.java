package org.example.post.controller;

import jakarta.validation.Valid;
import org.example.post.dto.request.PostUpdateRequest;
import org.example.post.dto.response.PostAllResponse;
import org.example.post.dto.request.PostRequest;
import org.example.post.dto.response.PostSearchByKeywordResponse;
import org.example.post.dto.response.PostDetailReponse;
import org.example.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/{userId}/post")
    public ResponseEntity<String> createPost(@PathVariable("userId") Long userId, @Valid @RequestBody final PostRequest postRequest) {
        postService.createPost(userId, postRequest);
        return ResponseEntity.ok().body("게시글이 작성되었습니다.");
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostAllResponse>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/{postId}/post")
    public ResponseEntity<PostDetailReponse> getPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.getPost(postId));
    }

    @PatchMapping("/{postId}/post")
    public ResponseEntity<String> updatePostTitle(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePostTitle(postId, postUpdateRequest);
        return ResponseEntity.ok().body("게시글이 수정되었습니다.");
    }

    @DeleteMapping("/{postId}/post")
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }

    @GetMapping("post/{keyword}")
    public PostSearchByKeywordResponse searchPostsByKeyword(@PathVariable("keyword") String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }
}
