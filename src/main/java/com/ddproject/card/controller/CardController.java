package com.ddproject.card.controller;

import com.ddproject.card.dto.CardDto;
import com.ddproject.card.entity.Card;
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
                                        @RequestBody CardDto cardDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if (!cardService.isUserAllowedToAccessCard(boardId, user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        CardDto createdCard = cardService.createCard(boardId, columnId, cardDto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @Operation(summary = "카드 시퀀스 업데이트")
    @PatchMapping("/{cardId}/sequence")
    public ResponseEntity<?> updateCardSequence(@PathVariable Long columnId,
                                                @PathVariable Long cardId,
                                                @RequestBody CardDto cardDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if (!cardService.isUserAllowedToAccessCard(columnId, user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        cardService.updateCardSequence(cardId, cardDto.getSequence());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카드 업데이트")
    @PatchMapping("/{cardId}")
    public ResponseEntity<?> updateCard(@PathVariable Long cardId,
                                        @RequestBody CardDto cardDto) {
        Card updatedCard = cardService.updateCard(cardId, cardDto);
        return updatedCard != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
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

