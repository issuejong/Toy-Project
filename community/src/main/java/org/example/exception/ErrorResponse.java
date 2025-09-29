package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
