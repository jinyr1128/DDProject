package com.ddproject.global.advice;

import com.ddproject.card.exception.CardException;
import com.ddproject.column.exception.ColumnException;
import com.ddproject.comment.exception.CommentException;
import com.ddproject.global.exception.CustomException;
import com.ddproject.global.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> userExceptionHandler(CustomException e) {
        log.error("Error occurs {}", e.toString());

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode().name(), e.getMsg()));
    }
    @ExceptionHandler(CardException.class)
    public ResponseEntity<?> cardExceptionHandler(CardException e) {
        log.error("Error occurs {}", e.toString());

        return ResponseEntity.status(e.getCardErrorCode().getStatus())
                .body(ApiResponse.error(e.getCardErrorCode().name(), e.getMsg()));
    }
    @ExceptionHandler(ColumnException.class)
    public ResponseEntity<?> columnExceptionHandler(ColumnException e) {
        log.error("Error occurs {}", e.toString());

        return ResponseEntity.status(e.getColumnErrorCode().getStatus())
                .body(ApiResponse.error(e.getColumnErrorCode().name(), e.getMsg()));
    }
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<?> commentExceptionHandler(CommentException e) {
        log.error("Error occurs {}", e.toString());

        return ResponseEntity.status(e.getCommentErrorCode().getStatus())
                .body(ApiResponse.error(e.getCommentErrorCode().name(), e.getMsg()));
    }

    //컨트롤러 유효성 검증 예외처리 핸들러
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException e) {
        log.error("Error occurs {}", e.toString());

        Map<String, String> errorMap = new HashMap<>();

        if (e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(ApiResponse.error(HttpStatus.EXPECTATION_FAILED.name(), errorMap));
    }
}
