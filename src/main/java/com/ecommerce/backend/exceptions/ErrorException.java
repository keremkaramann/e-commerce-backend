package com.ecommerce.backend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorException extends RuntimeException {
    private HttpStatus status;

    public ErrorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
