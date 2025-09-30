package org.example.post.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.entity.Post;

public record PostRequest(
        @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "제목은 30자 이내로 입력해주세요.")
    String title,
        @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요.")
    String content,
        Post.Tag tag) {}
