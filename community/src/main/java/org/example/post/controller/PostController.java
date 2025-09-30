package org.example.post.controller;

import jakarta.validation.Valid;
import org.example.entity.Post;
import org.example.post.dto.request.PostUpdateRequest;
import org.example.post.dto.response.PostAllResponse;
import org.example.post.dto.request.PostRequest;
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

    @PatchMapping("/post/{postId}")
    public ResponseEntity<String> updatePostTitle(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(postId, postUpdateRequest);
        return ResponseEntity.ok().body("게시글이 수정되었습니다.");
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostAllResponse>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDetailReponse> getPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.getDetailPost(postId));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }

    @GetMapping("post/search/title/{Title}")
    public ResponseEntity<List<PostAllResponse>> searchPostsByTitle(@PathVariable("Title") String Title) {
        return ResponseEntity.ok().body(postService.searchPostsByTitle(Title));
    }

    @GetMapping("post/search/nickname/{Nickname}")
    public ResponseEntity<List<PostAllResponse>> searchPostsByNickname(@PathVariable("Nickname") String Nickname) {
        return ResponseEntity.ok().body(postService.searchPostsByNickname(Nickname));
    }

    @GetMapping("post/search/tag/{Tag}")
    public ResponseEntity<List<PostAllResponse>> searchPostsByTag(@PathVariable("Tag")Post.Tag tag) {
        return ResponseEntity.ok().body(postService.searchPostsByTag(tag));
    }
}
