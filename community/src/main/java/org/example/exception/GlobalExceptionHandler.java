package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Business Error
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleGeneralException(BusinessException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().getHttpStatus().value(),
                e.getErrorCode().getErrorCode(),
                e.getErrorCode().getMessage()
        );

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * Validation Error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                e.getBindingResult().getFieldError().getDefaultMessage()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
