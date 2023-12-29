package com.ddproject.comment.service;

import com.ddproject.card.entity.Card;
import com.ddproject.card.repository.CardRepository;
import com.ddproject.comment.dto.CommentDto;
import com.ddproject.comment.entity.Comment;
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

    public CommentDto createComment(Long cardId, CommentDto commentDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Comment comment = new Comment();
        comment.setCard(card);
        comment.setText(commentDto.getText());
        comment.setAuthorId(commentDto.getAuthorId());

        Comment savedComment = commentRepository.save(comment);
        return convertEntityToDto(savedComment);
    }

    public List<CommentDto> getCommentsByCardId(Long cardId) {
        return commentRepository.findByCardId(cardId).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public CommentDto updateComment(Long commentId, CommentDto commentDto, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAuthorId().equals(userId)) {
            throw new RuntimeException("Only the author can update the comment");
        }

        comment.setText(commentDto.getText());
        Comment updatedComment = commentRepository.save(comment);
        return convertEntityToDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAuthorId().equals(userId)) {
            throw new RuntimeException("Only the author can delete the comment");
        }

        commentRepository.deleteById(commentId);
    }
    private CommentDto convertEntityToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setAuthorId(comment.getAuthorId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}