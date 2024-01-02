package com.ddproject.card.service;

import com.ddproject.board.repository.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.card.dto.CardRequest;
import com.ddproject.card.dto.CardResponse;
import com.ddproject.card.entity.Card;
import com.ddproject.card.entity.QCard;
import com.ddproject.card.exception.CardErrorCode;
import com.ddproject.card.exception.CardException;
import com.ddproject.member.repository.BoardMemberRepository;
import com.ddproject.card.repository.CardRepository;
import com.ddproject.column.entity.Column;
import com.ddproject.column.repository.ColumnRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
@RequiredArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    public boolean isUserAllowedToAccessCard(Long columnId, Long userId) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new CardException(CardErrorCode.COLUMN_NOT_FOUND));
        Long boardId = column.getBoard().getId();
        return boardMemberRepository.existsByBoardIdAndUserId(boardId, userId);
    }

    @Transactional
    public CardResponse createCard(Long boardId, Long columnId, CardRequest request) {
        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new CardException(CardErrorCode.BOARD_NOT_FOUND));
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new CardException(CardErrorCode.COLUMN_NOT_FOUND));

        // 카드 객체 생성 및 저장
        Card card = new Card();
        card.setColumn(column);
        card.setName(request.getName());
        card.setDescription(request.getDescription());
        card.setColor(request.getColor());

        // 기본 순서 설정
        int maxSequence = cardRepository.findMaxSequenceByColumnId(columnId);
        card.setSequence(maxSequence + 1);

        Card savedCard = cardRepository.save(card);
        return convertEntityToResponse(savedCard);
    }


    @Transactional
    public void updateCardSequence(Long cardId, Integer newSequence) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));

        int oldSequence = card.getSequence();
        Long columnId = card.getColumn().getId();

        // 카드의 새 순서를 설정
        card.setSequence(newSequence);
        cardRepository.save(card);

        // 관련된 다른 카드들의 순서도 업데이트
        updateOtherCardsSequence(columnId, cardId, oldSequence, newSequence);
    }

    @Transactional
    public void updateOtherCardsSequence(Long columnId, Long cardId, Integer oldSequence, Integer newSequence) {
        QCard qCard = QCard.card;

        if (oldSequence > newSequence) {
            // 카드가 앞으로 이동하는 경우, 중간의 카드들을 뒤로 밀어
            queryFactory.update(qCard)
                    .set(qCard.sequence, qCard.sequence.add(1))
                    .where(qCard.column.id.eq(columnId)
                            .and(qCard.sequence.between(newSequence, oldSequence - 1))
                            .and(qCard.id.ne(cardId)))
                    .execute();
        } else if (oldSequence < newSequence) {
            // 카드가 뒤로 이동하는 경우, 중간의 카드들을 앞으로 당겨
            queryFactory.update(qCard)
                    .set(qCard.sequence, qCard.sequence.subtract(1))
                    .where(qCard.column.id.eq(columnId)
                            .and(qCard.sequence.between(oldSequence + 1, newSequence))
                            .and(qCard.id.ne(cardId)))
                    .execute();
        }
        // 만약 순서가 같지 않다면, 즉 순서가 바뀌지 않았다면 아무 것도 하지 x
    }
    @Transactional
    public CardResponse updateCard(Long cardId, CardRequest request) {
        return cardRepository.findById(cardId).map(card -> {
            boolean updated = false;

            if (!Objects.equals(card.getName(), request.getName())) {
                card.setName(request.getName());
                updated = true;
            }
            if (!Objects.equals(card.getDescription(), request.getDescription())) {
                card.setDescription(request.getDescription());
                updated = true;
            }
            if (!Objects.equals(card.getColor(), request.getColor())) {
                card.setColor(request.getColor());
                updated = true;
            }
            if (request.getSequence() != null && !Objects.equals(card.getSequence(), request.getSequence())) {
                card.setSequence(request.getSequence());
                updated = true;
            }

            if (updated) {
                Card updatedCard = cardRepository.save(card);
                return convertEntityToResponse(updatedCard);
            } else {
                return convertEntityToResponse(card);
            }
        }).orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
    }
    @Transactional
    public boolean deleteCard(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new CardException(CardErrorCode.CARD_NOT_FOUND);
        }
        cardRepository.deleteById(cardId);
        return true;
    }

    @Transactional
    public void moveCardToAnotherColumn(Long cardId, Long newColumnId, int newSequence) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
        Column newColumn = columnRepository.findById(newColumnId)
                .orElseThrow(() -> new CardException(CardErrorCode.COLUMN_NOT_FOUND));

        card.setColumn(newColumn);
        card.setSequence(newSequence);
        cardRepository.save(card);
    }
    private CardResponse convertEntityToResponse(Card card) {
        return new CardResponse(
                card.getId(),
                card.getName(),
                card.getDescription(),
                card.getColor(),
                card.getSequence()
        );
    }
}
//    @Transactional
//    public void updateCardName(Long cardId, String newName) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        card.setName(newName);
//        cardRepository.save(card);
//    }
//
//    @Transactional
//    public void updateCardColor(Long cardId, String newColor) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        card.setColor(newColor);
//        cardRepository.save(card);
//    }
//
//    @Transactional
//    public void updateCardDescription(Long cardId, String newDescription) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        card.setDescription(newDescription);
//        cardRepository.save(card);
//    }
//
//    @Transactional
//    public void deleteCard(Long cardId) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        cardRepository.delete(card);
//    }

