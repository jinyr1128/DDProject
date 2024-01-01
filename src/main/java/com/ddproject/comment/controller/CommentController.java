package com.ddproject.comment.controller;

import com.ddproject.comment.dto.CommentDto;
import com.ddproject.comment.service.CommentService;
import com.ddproject.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable Long cardId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(cardId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "카드에 달린 댓글 전체 조회")
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentDto> comments = commentService.getCommentsByCardId(cardId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 업데이트")
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentDto commentDto,
                                                    @AuthenticationPrincipal User user) {
        CommentDto updatedComment = commentService.updateComment(commentId, commentDto, user.getId());
        return ResponseEntity.ok(updatedComment);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.ok().build();
    }
}
