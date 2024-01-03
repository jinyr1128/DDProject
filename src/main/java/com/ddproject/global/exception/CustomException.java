package com.ddproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private String msg;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.msg = null;
    }
}
