package com.ddproject.comment.controller;

import com.ddproject.comment.dto.CommentDto;
import com.ddproject.comment.service.CommentService;
import com.ddproject.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/columns/{columnId}/cards/{cardId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable Long cardId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(cardId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentDto> comments = commentService.getCommentsByCardId(cardId);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentDto commentDto,
                                                    @AuthenticationPrincipal User user) {
        CommentDto updatedComment = commentService.updateComment(commentId, commentDto, user.getId());
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.ok().build();
    }
}
