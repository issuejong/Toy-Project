package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    /**
     * MisMatch Error
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMisMatchException(MethodArgumentTypeMismatchException e) {
        Class<?> required = e.getRequiredType();
        String param = e.getName();
        String value = String.valueOf(e.getValue());

        String allowed = Arrays.stream(required.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining("Backend, Infra, Database"));

        ErrorResponse body = new ErrorResponse(
                400,
                "INVALID_ENUM_VALUE",
                "올바른 태그를 입력해주세요."
        );
        return ResponseEntity.badRequest().body(body);
    }
}
