package com.ddproject.card.exception;

import lombok.Getter;

@Getter
public enum CardErrorCode {
    BOARD_NOT_FOUND(404, "보드를 찾을 수 없습니다."),
    COLUMN_NOT_FOUND(404, "컬럼을 찾을 수 없습니다."),
    CARD_NOT_FOUND(404, "카드를 찾을 수 없습니다."),
    INVALID_CARD_DATA(400, "잘못된 카드 데이터입니다."),
    CARD_UPDATE_FAILED(500, "카드 업데이트에 실패했습니다."),
    CARD_DELETE_FAILED(500, "카드 삭제에 실패했습니다."),
    CARD_CREATION_FAILED(500, "카드 생성에 실패했습니다."),
    UNAUTHORIZED_CARD_ACCESS(401, "카드 접근 권한이 없습니다."),
    FORBIDDEN_CARD_ACCESS(403, "카드 접근이 금지되었습니다."),
    INTERNAL_CARD_SERVER_ERROR(500, "카드 관련 서버 내부 오류가 발생했습니다.");

    private final int statusCode;
    private final String message;

    CardErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}