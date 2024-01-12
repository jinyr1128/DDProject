package com.ddproject.comment.controller;

import com.ddproject.comment.dto.CommentRequest;
import com.ddproject.comment.dto.CommentResponse;
import com.ddproject.comment.service.CommentService;
import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.global.response.ApiResponse;
import com.ddproject.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards/{boardId}/columns/{columnId}/cards/{cardId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@PathVariable Long cardId,
                                                                      @RequestBody CommentRequest request,
                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getId();
        CommentResponse createdComment = commentService.createComment(cardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success(createdComment));
    }


    @Operation(summary = "카드에 달린 댓글 전체 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentResponse> comments = commentService.getCommentsByCardId(cardId);
        return ResponseEntity.ok(ApiResponse.success(comments));
    }

    @Operation(summary = "댓글 업데이트")
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(@PathVariable Long commentId,
                                                         @RequestBody CommentRequest request,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        CommentResponse updatedComment = commentService.updateComment(commentId, request, user.getId());
        return ResponseEntity.ok(ApiResponse.success(updatedComment));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.ok(ApiResponse.success());
    }
}