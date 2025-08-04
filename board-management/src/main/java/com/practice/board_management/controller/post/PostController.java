package com.practice.board_management.controller.post;

import com.practice.board_management.domain.post.Post;
import com.practice.board_management.dto.post.request.PostCreateRequest;
import com.practice.board_management.dto.post.response.PersonalPostResponse;
import com.practice.board_management.dto.post.response.PostResponse;
import com.practice.board_management.dto.post.response.eachByUser.PostEachByUserResponse;
import com.practice.board_management.dto.post.response.entireUsers.PostEntireUsersResponse;
import com.practice.board_management.service.jwt.UserDetailsImpl;
import com.practice.board_management.service.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RestController
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("users/me/posts")
    public void createPost(@RequestBody PostCreateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(request, userDetails.getUser());
    }

    @GetMapping("/users/me/posts")
    public PostEachByUserResponse getPost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPostByPersonal(userDetails.getUser().getUserId());
    }

    @DeleteMapping("/users/me/{postId}")
    public void deletePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
    }
}
