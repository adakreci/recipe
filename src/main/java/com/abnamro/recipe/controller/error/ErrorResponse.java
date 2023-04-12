package com.abnamro.recipe.controller.error;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private final Integer httpStatus;
    private final String exception;
    private final String message;
}
