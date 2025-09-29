package org.example.post.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(String title, String content) {
}
