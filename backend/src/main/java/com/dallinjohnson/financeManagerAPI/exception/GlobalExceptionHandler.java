package com.dallinjohnson.financeManagerAPI.exception;

import com.dallinjohnson.financeManagerAPI.dto.ApiErrorDTO;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Common method to handle the creation of ApiError response
    private ResponseEntity<ApiErrorDTO> buildApiErrorResponse(HttpStatus status, String title, String detail, WebRequest request, List<ApiErrorDTO.ErrorItem> errorItems) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                LocalDateTime.now(),
                status.value(),
                title,
                detail,
                request.getDescription(false).replace("uri=", ""),
                errorItems
        );
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationException(MethodArgumentNotValidException exception, WebRequest request) {
        List<ApiErrorDTO.ErrorItem> errorItems = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiErrorDTO.ErrorItem(error.getField(), error.getDefaultMessage()))
                .toList();

        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request Body", "Request body contains invalid data", request, errorItems);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDTO> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        List<ApiErrorDTO.ErrorItem> errorItems = exception.getConstraintViolations().stream()
                .map(violation -> new ApiErrorDTO.ErrorItem(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();

        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", "Request contains invalid parameters", request, errorItems);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleEntityNotFound(EntityNotFoundException exception, WebRequest request) {
        return buildApiErrorResponse(HttpStatus.NOT_FOUND, "Requested resource could not be found", exception.getMessage(), request, List.of());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleHandlerNotFound(NoHandlerFoundException exception, WebRequest request) {
        return buildApiErrorResponse(HttpStatus.NOT_FOUND, "Endpoint not found", exception.getMessage(), request, List.of());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleResourceNotFound(NoResourceFoundException exception, WebRequest request) {
        return buildApiErrorResponse(HttpStatus.NOT_FOUND, "Static resource not found", exception.getMessage(), request, List.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDTO> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception, WebRequest request) {

        List<ApiErrorDTO.ErrorItem> errorItems = new ArrayList<>();

        if (exception.getMostSpecificCause() instanceof JsonMappingException mappingException) {
            for(JsonMappingException.Reference reference : mappingException.getPath()) {
                errorItems.add(new ApiErrorDTO.ErrorItem(reference.getFieldName(), "Invalid type provided"));
            }
        } else {
            errorItems.add(new ApiErrorDTO.ErrorItem("Unknown field", "Invalid type provided"));
        }

        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request format", exception.getMessage(), request, errorItems);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGeneralException(Exception exception, WebRequest request) {
        List<ApiErrorDTO.ErrorItem> errorItems = List.of(new ApiErrorDTO.ErrorItem("general", "An unexpected error occurred. Please try again later."));
        return buildApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", exception.getMessage(), request, errorItems);
    }
}
