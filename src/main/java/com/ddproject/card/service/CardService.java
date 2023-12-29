package com.ddproject.card.service;

import com.ddproject.board.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.card.dto.CardDto;
import com.ddproject.card.entity.Card;
import com.ddproject.card.entity.QCard;
import com.ddproject.member.BoardMemberRepository;
import com.ddproject.card.repository.CardRepository;
import com.ddproject.column.entity.Column;
import com.ddproject.column.repository.ColumnRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CardService(CardRepository cardRepository,
                       BoardMemberRepository boardMemberRepository,
                       ColumnRepository columnRepository,
                       BoardRepository boardRepository,
                       EntityManager entityManager) {
        this.cardRepository = cardRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public boolean isUserAllowedToAccessCard(Long columnId, Long userId) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        Long boardId = column.getBoard().getId();
        return boardMemberRepository.existsByBoardIdAndUserId(boardId, userId);
    }


    @Transactional
    public CardDto createCard(Long boardId, Long columnId, CardDto cardDto) {
        // 보드와 칼럼 존재 여부 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        // 카드 객체 생성 및 저장
        Card card = new Card();
        card.setColumn(column);
        card.setName(cardDto.getName());
        card.setDescription(cardDto.getDescription());
        card.setColor(cardDto.getColor());

        // 기본 순서를 마지막으로 설정
        int maxSequence = cardRepository.findMaxSequenceByColumnId(columnId);
        card.setSequence(maxSequence + 1);

        Card savedCard = cardRepository.save(card);

        return convertEntityToDto(savedCard);
    }

    @Transactional
    public void updateCardSequence(Long cardId, int newSequence) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

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

    private CardDto convertEntityToDto(Card card) {
        CardDto dto = new CardDto();
        dto.setCardId(card.getId());
        dto.setName(card.getName());
        dto.setDescription(card.getDescription());
        dto.setColor(card.getColor());
        dto.setSequence(card.getSequence());
        return dto;
    }

    public Card updateCard(Long cardId, CardDto cardDto) {
        return cardRepository.findById(cardId).map(card -> {
            boolean updated = false;

            if (!Objects.equals(card.getName(), cardDto.getName())) {
                card.setName(cardDto.getName());
                updated = true;
            }
            if (!Objects.equals(card.getDescription(), cardDto.getDescription())) {
                card.setDescription(cardDto.getDescription());
                updated = true;
            }
            if (!Objects.equals(card.getColor(), cardDto.getColor())) {
                card.setColor(cardDto.getColor());
                updated = true;
            }
            if (!Objects.equals(card.getSequence(), cardDto.getSequence())) {
                card.setSequence(cardDto.getSequence());
                updated = true;
            }

            if (updated) {
                return cardRepository.save(card);
            } else {
                return null;
            }
        }).orElse(null);
    }

    @Transactional
    public boolean deleteCard(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            return false;
        }
        cardRepository.deleteById(cardId);
        return true;
    }

    @Transactional
    public void moveCardToAnotherColumn(Long cardId, Long newColumnId, int newSequence) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        Column newColumn = columnRepository.findById(newColumnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        card.setColumn(newColumn);
        card.setSequence(newSequence);
        cardRepository.save(card);
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

