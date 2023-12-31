package com.ddproject.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentException extends RuntimeException {
    private final CommentErrorCode commentErrorCode;
    private final String msg;

    public CommentException(CommentErrorCode commentErrorCode) {
        super(commentErrorCode.getMessage());
        this.commentErrorCode = commentErrorCode;
        this.msg = commentErrorCode.getMessage();
    }
}
