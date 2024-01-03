package com.ddproject.card.controller;

import com.ddproject.card.dto.CardRequest;
import com.ddproject.card.dto.CardResponse;
import com.ddproject.card.exception.CardErrorCode;
import com.ddproject.card.exception.CardException;
import com.ddproject.card.service.CardService;
import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/columns/{columnId}/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "카드 생성")
    @PostMapping
    public ResponseEntity<?> createCard(@PathVariable Long boardId,
                                        @PathVariable Long columnId,
                                        @RequestBody CardRequest request,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        try {
            if (!cardService.isUserAllowedToAccessCard(boardId, user.getId())) {
                throw new CardException(CardErrorCode.FORBIDDEN_CARD_ACCESS);
            }
            CardResponse createdCard = cardService.createCard(boardId, columnId, request);
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (CardException e) {
            return ResponseEntity
                    .status(e.getCardErrorCode().getStatus())
                    .body(e.getMsg());
        }
    }
    @Operation(summary = "카드 시퀀스 업데이트")
    @PatchMapping("/{cardId}/sequence")
    public ResponseEntity<?> updateCardSequence(@PathVariable Long columnId,
                                                @PathVariable Long cardId,
                                                @RequestBody CardRequest request,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if (!cardService.isUserAllowedToAccessCard(columnId, user.getId())) {
            throw new CardException(CardErrorCode.FORBIDDEN_CARD_ACCESS);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        cardService.updateCardSequence(cardId, request.getSequence());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카드 업데이트")
    @PatchMapping("/{cardId}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable Long cardId,
                                                   @RequestBody CardRequest request) {
        CardResponse updatedCard = cardService.updateCard(cardId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @Operation(summary = "카드 삭제")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
        boolean deleted = cardService.deleteCard(cardId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "카드 위치 변경")
    @PatchMapping("/{cardId}/move")
    public ResponseEntity<?> moveCard(@PathVariable Long cardId,
                                      @RequestParam Long newColumnId,
                                      @RequestParam int newSequence,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if (!cardService.isUserAllowedToAccessCard(newColumnId, user.getId())) {
            throw new CardException(CardErrorCode.FORBIDDEN_CARD_ACCESS);
        }
        cardService.moveCardToAnotherColumn(cardId, newColumnId, newSequence);
        return ResponseEntity.ok().build();
    }
}
//    @PatchMapping("/{cardId}/name")
//    public ResponseEntity<?> updateCardName(@PathVariable Long columnId,
//                                            @PathVariable Long cardId,
//                                            @RequestBody CardDto cardDto,
//                                            @AuthenticationPrincipal User user) {
//        // 권한 확인 및 카드 이름 업데이트 로직
//        // cardService.updateCardName(cardId, cardDto.getName());
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{cardId}/color")
//    public ResponseEntity<?> updateCardColor(@PathVariable Long columnId,
//                                             @PathVariable Long cardId,
//                                             @RequestBody CardDto cardDto,
//                                             @AuthenticationPrincipal User user) {
//        // 권한 확인 및 카드 색상 업데이트 로직
//        // cardService.updateCardColor(cardId, cardDto.getColor());
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{cardId}/script")
//    public ResponseEntity<?> updateCardDescription(@PathVariable Long columnId,
//                                                   @PathVariable Long cardId,
//                                                   @RequestBody CardDto cardDto,
//                                                   @AuthenticationPrincipal User user) {
//        // 권한 확인 및 카드 설명 업데이트 로직
//        // cardService.updateCardDescription(cardId, cardDto.getDescription());
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{cardId}")
//    public ResponseEntity<?> deleteCard(@PathVariable Long columnId,
//                                        @PathVariable Long cardId,
//                                        @AuthenticationPrincipal User user) {
//        // 권한 확인 및 카드 삭제 로직
//        // cardService.deleteCard(cardId);
//        return ResponseEntity.ok().build();
//    }

