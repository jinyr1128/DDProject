package com.ddproject.card.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CardErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "보드를 찾을 수 없습니다."),
    COLUMN_NOT_FOUND(HttpStatus.NOT_FOUND, "컬럼을 찾을 수 없습니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."),
    INVALID_CARD_DATA(HttpStatus.BAD_REQUEST, "잘못된 카드 데이터입니다."),
    CARD_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카드 업데이트에 실패했습니다."),
    CARD_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카드 삭제에 실패했습니다."),
    CARD_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카드 생성에 실패했습니다."),
    UNAUTHORIZED_CARD_ACCESS(HttpStatus.UNAUTHORIZED, "카드 접근 권한이 없습니다."),
    FORBIDDEN_CARD_ACCESS(HttpStatus.FORBIDDEN, "카드 접근이 금지되었습니다."),
    INTERNAL_CARD_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카드 관련 서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    CardErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
