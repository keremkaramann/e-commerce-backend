package com.ecommerce.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GeneralErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime dateTime;
}
