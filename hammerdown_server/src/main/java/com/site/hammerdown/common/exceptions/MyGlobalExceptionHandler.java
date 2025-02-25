package com.site.hammerdown.common.exceptions;

import com.site.hammerdown.common.model.APIResponseStatus;
import com.site.hammerdown.common.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> myMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException err) {
        Map<String, String> invalidArguments = new HashMap<>();

        err.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            invalidArguments.put(fieldName, errorMessage);
        });

        APIResponse response = APIResponse.builder()
                .data(invalidArguments)
                .status(APIResponseStatus.FAILURE)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();

        return response.generateResponseEntity();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        APIResponse response = APIResponse.builder()
                .errorMessage(e.getMessage())
                .status(APIResponseStatus.FAILURE)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();

        return response.generateResponseEntity();
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e) {
        int statusCode = Objects.isNull(e.getStatusCode())
                ? HttpStatus.BAD_REQUEST.value()
                : e.getStatusCode();

        APIResponse response = APIResponse.builder()
                .errorMessage(e.getMessage())
                .status(APIResponseStatus.FAILURE)
                .statusCode(statusCode)
                .build();

        return response.generateResponseEntity();
    }
}
