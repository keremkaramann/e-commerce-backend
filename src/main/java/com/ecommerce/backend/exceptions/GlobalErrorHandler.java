package com.ecommerce.backend.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<GeneralErrorResponse> handleErrorException(ErrorException errorException) {
        GeneralErrorResponse generalException = new GeneralErrorResponse(errorException.getStatus().value(),
                errorException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(generalException, errorException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidationException(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errorList = exception.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> {
                    log.warn(fieldError.getField() + ": " + fieldError.getDefaultMessage());
                    Map<String, String> errors = new HashMap<>();
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errors;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponse> handleGenericException(Exception exception) {
        GeneralErrorResponse errorResponse = new GeneralErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(), LocalDateTime.now());
        log.error("EXCEPTION OCCURRED: " + exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}