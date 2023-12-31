package com.ddproject.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String text;
    private Long authorId;
    private ZonedDateTime createdAt;
}
