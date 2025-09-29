package org.example.post.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.entity.Post;
import org.example.exception.BusinessException;
import org.example.exception.ErrorCode;
import org.example.post.dto.response.PostAllResponse;
import org.example.post.dto.request.PostRequest;
import org.example.post.dto.response.PostDetailReponse;
import org.example.post.dto.response.PostSearchByKeywordResponse;
import org.example.post.dto.request.PostUpdateRequest;
import org.example.post.repository.PostRepository;
import org.example.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private LocalDateTime lastPostTime;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void createPost(Long userId, @Valid PostRequest request) {
        Post post = new Post(request.title(), request.content(), userRepository.getUserById(userId));
//        if(lastPostTime != null && Duration.between(lastPostTime, LocalDateTime.now()).toMinutes() < 3)
//            throw new IllegalArgumentException("잠시 후에 다시 작성해 주세요.");
        postRepository.save(post);
    }

    public List<PostAllResponse> getAllPosts() {
        List<PostAllResponse> postAllResponses = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        posts = postRepository.findAll();

        for(Post post : posts) {
            postAllResponses.add(new PostAllResponse(post.getTitle(), post.getUser().getNickname()));
        }

        return postAllResponses;
    }

    public PostDetailReponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));

        return new PostDetailReponse(post.getUser().getNickname(), post.getTitle(), post.getContent());
    }

    public void updatePostTitle(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));
        post.update(
                request.title() == null ? post.getTitle() : request.title(),
                request.content() == null ? post.getContent() : request.content());
    }

    public PostSearchByKeywordResponse searchPostsByKeyword(String keyword) {
        List<Post> posts = postRepository.findAll();
        List<Post> responsePostList = new ArrayList<>();
        for(Post post : posts) {
            if(post.getTitle().contains(keyword)) responsePostList.add(post);
        }
        return new PostSearchByKeywordResponse(responsePostList);
    }

    public void deletePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));
        postRepository.delete(post);
    }

}
