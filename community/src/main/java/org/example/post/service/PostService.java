package org.example.post.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.comment.dto.response.CommentResponse;
import org.example.comment.repository.CommentRepository;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.exception.BusinessException;
import org.example.exception.ErrorCode;
import org.example.post.dto.response.PostAllResponse;
import org.example.post.dto.request.PostRequest;
import org.example.post.dto.response.PostDetailReponse;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private LocalDateTime lastPostTime;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public void createPost(Long userId, @Valid PostRequest request) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
        Post post = new Post(request.title(), request.content(), request.tag(), userRepository.getUserById(userId));
//        if(lastPostTime != null && Duration.between(lastPostTime, LocalDateTime.now()).toMinutes() < 3)
//            throw new IllegalArgumentException("잠시 후에 다시 작성해 주세요.");
        postRepository.save(post);
    }

    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));
        post.update(
                request.title() == null ? post.getTitle() : request.title(),
                request.content() == null ? post.getContent() : request.content());
    }

    public List<PostAllResponse> getAllPosts() {
        List<PostAllResponse> postAllResponses = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        posts = postRepository.findAll();

        for(Post post : posts) {
            postAllResponses.add(new PostAllResponse(post.getTitle(), post.getUser().getNickname(), post.getTag()));
        }
        return postAllResponses;
    }

    public PostDetailReponse getDetailPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));
        List<Comment> comments = commentRepository.findAllByPostId(id);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment : comments) {
            commentResponses.add(new CommentResponse(
                    comment.getUser().getNickname(),
                    comment.getContent()));
        }
        return new PostDetailReponse(post.getUser().getNickname(), post.getTitle(), post.getContent(), commentResponses);
    }

    public void deletePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST));
        postRepository.delete(post);
    }

    public List<PostAllResponse> searchPostsByTitle(String keyword) {
        List<Post> posts = postRepository.findAll();
        List<PostAllResponse> responsePostList = new ArrayList<>();
        for(Post post : posts) {
            if(post.getTitle().contains(keyword)) responsePostList.add(
                    new PostAllResponse(post.getUser().getNickname(), post.getTitle(), post.getTag()));
        }
        if(responsePostList.isEmpty()) throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        return responsePostList;
    }

    public List<PostAllResponse> searchPostsByNickname(String keyword) {
        List<Post> posts = postRepository.findAll();
        List<PostAllResponse> responsePostList = new ArrayList<>();
        for(Post post : posts) {
            if(post.getUser().getNickname().contains(keyword)) responsePostList.add(
                    new PostAllResponse(post.getUser().getNickname(), post.getTitle(), post.getTag()));
        }
        if(responsePostList.isEmpty()) throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        return responsePostList;
    }

    public List<PostAllResponse> searchPostsByTag(Post.Tag tag) {
        List<Post> posts = postRepository.findByTag(tag);
        List<PostAllResponse> responsePostList = new ArrayList<>();
        for(Post post : posts) {
            responsePostList.add(
                    new PostAllResponse(post.getUser().getNickname(), post.getTitle(), post.getTag()));
        }
        if(responsePostList.isEmpty()) throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        return responsePostList;
    }
}
