package com.abnamro.recipe.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleStatusException(final ResponseStatusException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .exception(exception.getReason())
                        .httpStatus(exception.getStatusCode().value())
                        .message(exception.getMessage())
                        .build(),
                exception.getStatusCode());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .exception(exception.getLocalizedMessage())
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
