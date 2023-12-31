package com.ddproject.column.exception;

import lombok.Getter;

@Getter
public enum ColumnErrorCode {
    BOARD_NOT_FOUND(404, "보드를 찾을 수 없습니다."),
    COLUMN_NOT_FOUND(404, "컬럼을 찾을 수 없습니다."),
    INVALID_COLUMN_DATA(400, "잘못된 컬럼 데이터입니다."),
    COLUMN_UPDATE_FAILED(500, "컬럼 업데이트에 실패했습니다."),
    COLUMN_DELETE_FAILED(500, "컬럼 삭제에 실패했습니다."),
    COLUMN_CREATION_FAILED(500, "컬럼 생성에 실패했습니다."),
    UNAUTHORIZED_ACCESS(401, "인증되지 않은 접근입니다."),
    FORBIDDEN_ACCESS(403, "접근이 금지되었습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int statusCode;
    private final String message;

    ColumnErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
