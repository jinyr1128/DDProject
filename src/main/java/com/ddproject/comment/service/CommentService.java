package com.ddproject.comment.service;

import com.ddproject.card.entity.Card;
import com.ddproject.card.repository.CardRepository;
import com.ddproject.comment.dto.CommentRequest;
import com.ddproject.comment.dto.CommentResponse;
import com.ddproject.comment.entity.Comment;
import com.ddproject.comment.exception.CommentErrorCode;
import com.ddproject.comment.exception.CommentException;
import com.ddproject.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, CardRepository cardRepository) {
        this.commentRepository = commentRepository;
        this.cardRepository = cardRepository;
    }

    public CommentResponse createComment(Long cardId, CommentRequest request, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.CARD_NOT_FOUND));

        Comment comment = new Comment();
        comment.setCard(card);
        comment.setText(request.getText());
        comment.setAuthorId(userId);// 댓글 작성자 ID는 인증된 사용자의 ID로 설정

        Comment savedComment = commentRepository.save(comment);
        return convertEntityToResponse(savedComment);
    }

    public List<CommentResponse> getCommentsByCardId(Long cardId) {
        return commentRepository.findByCardId(cardId).stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getAuthorId().equals(userId)) {
            throw new CommentException(CommentErrorCode.UNAUTHORIZED_COMMENT_ACCESS);
        }

        comment.setText(request.getText());
        Comment updatedComment = commentRepository.save(comment);
        return convertEntityToResponse(updatedComment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getAuthorId().equals(userId)) {
            throw new CommentException(CommentErrorCode.UNAUTHORIZED_COMMENT_ACCESS);
        }

        commentRepository.deleteById(commentId);
    }

    private CommentResponse convertEntityToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthorId(),
                comment.getCreatedAt()
        );
    }
}