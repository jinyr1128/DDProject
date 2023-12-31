package com.ddproject.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardException extends RuntimeException {
    private final CardErrorCode cardErrorCode;
    private final String msg;

    public CardException(CardErrorCode cardErrorCode) {
        super(cardErrorCode.getMessage());
        this.cardErrorCode = cardErrorCode;
        this.msg = cardErrorCode.getMessage();
    }
}
