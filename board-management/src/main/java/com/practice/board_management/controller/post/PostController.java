package com.practice.board_management.controller.post;

import com.practice.board_management.dto.post.request.PostCreateRequest;
import com.practice.board_management.dto.post.response.eachByUser.PostEachByUserResponse;
import com.practice.board_management.dto.post.response.entireUsers.PostEntireUsersResponse;
import com.practice.board_management.service.post.PostService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board/posts")
@RestController
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void createPost(@RequestBody PostCreateRequest request, @RequestParam Long userId) {
        postService.createPost(request, userId);
    }

    @GetMapping("/each/{userId}")
    public PostEachByUserResponse getPostByPersonal(@PathVariable("userId") Long userId) {
        return postService.getPostByPersonal(userId);
    }

    @GetMapping("/entire/{userId}")
    public PostEntireUsersResponse getPostByEntire(@PathVariable("userId") Long userId) {
        return postService.getPostByEntire(userId);
    }
}
