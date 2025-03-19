package com.dallinjohnson.financeManagerAPI.exception;

import com.dallinjohnson.financeManagerAPI.dto.ApiError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException exception, WebRequest request) {
        List<ApiError.ErrorItem> errorItems = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiError.ErrorItem(error.getField(), error.getDefaultMessage()))
                .toList();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request Body",
                "Request body contains invalid data",
                request.getDescription(false).replace("uri=", ""),
                errorItems
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        List<ApiError.ErrorItem> errorItems = exception.getConstraintViolations().stream()
                .map(violation -> new ApiError.ErrorItem(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Request contains invalid parameters",
                request.getDescription(false).replace("uri=", ""),
                errorItems
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}