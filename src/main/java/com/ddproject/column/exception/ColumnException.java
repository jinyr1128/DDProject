package com.ddproject.column.exception;

import com.ddproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class ColumnException extends RuntimeException {
    private final ColumnErrorCode columnErrorCode;
    private final String msg;

    public ColumnException(ColumnErrorCode columnErrorCode) {
        super(columnErrorCode.getMessage());
        this.columnErrorCode = columnErrorCode;
        this.msg = columnErrorCode.getMessage();
    }
}