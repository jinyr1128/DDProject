package com.ddproject.column.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ColumnErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "보드를 찾을 수 없습니다."),
    COLUMN_NOT_FOUND(HttpStatus.NOT_FOUND, "컬럼을 찾을 수 없습니다."),
    INVALID_COLUMN_DATA(HttpStatus.BAD_REQUEST, "잘못된 컬럼 데이터입니다."),
    COLUMN_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "컬럼 업데이트에 실패했습니다."),
    COLUMN_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "컬럼 삭제에 실패했습니다."),
    COLUMN_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "컬럼 생성에 실패했습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근이 금지되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ColumnErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
